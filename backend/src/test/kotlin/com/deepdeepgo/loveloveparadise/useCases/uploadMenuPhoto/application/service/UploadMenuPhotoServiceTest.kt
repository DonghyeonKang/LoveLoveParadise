package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.service

import com.deepdeepgo.loveloveparadise.config.exception.InvalidRequestException
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.`in`.UploadMenuPhotoCmd
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.GeneratePhotoVariantsPort
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.PhotoVariants
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.SaveMenuPhotoPort
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.StoreMenuPhotoPort
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class UploadMenuPhotoServiceTest {

  @Mock private lateinit var validateTokenPort: ValidateTokenPort
  @Mock private lateinit var loadUserFamilyPort: LoadUserFamilyPort
  @Mock private lateinit var generatePhotoVariantsPort: GeneratePhotoVariantsPort
  @Mock private lateinit var saveMenuPhotoPort: SaveMenuPhotoPort
  @Mock private lateinit var storeMenuPhotoPort: StoreMenuPhotoPort

  private lateinit var service: UploadMenuPhotoService

  @BeforeEach
  fun setUp() {
    service =
      UploadMenuPhotoService(
        validateTokenPort,
        loadUserFamilyPort,
        generatePhotoVariantsPort,
        saveMenuPhotoPort,
        storeMenuPhotoPort,
      )
  }

  @Test
  @DisplayName("Scenario: 성공 - JPEG 이미지를 업로드하면 리사이즈 후 저장하고 photoId를 반환한다")
  fun upload_photo_success() {
    // Given
    val content = byteArrayOf(1, 2, 3)
    val cmd = UploadMenuPhotoCmd("valid.jwt.token", "image/jpeg", content)
    val variants = PhotoVariants(byteArrayOf(1), byteArrayOf(2), byteArrayOf(3))
    whenever(validateTokenPort.validate(cmd.accessToken)).thenReturn("user-uuid")
    whenever(loadUserFamilyPort.load("user-uuid")).thenReturn("family-uuid")
    whenever(generatePhotoVariantsPort.generate(content)).thenReturn(variants)
    whenever(saveMenuPhotoPort.save("family-uuid")).thenReturn("photo-uuid")

    // When
    val result = service.operate(cmd)

    // Then
    assertThat(result.photoId).isEqualTo("photo-uuid")
    verify(storeMenuPhotoPort).store("photo-uuid", variants)
  }

  @Test
  @DisplayName("Scenario: 실패 - 허용되지 않는 파일 형식이면 InvalidRequestException을 던지고 후속 처리를 하지 않는다")
  fun upload_photo_fail_invalid_content_type() {
    // Given
    val cmd = UploadMenuPhotoCmd("valid.jwt.token", "image/webp", byteArrayOf(1, 2, 3))

    // When & Then
    assertThatThrownBy { service.operate(cmd) }.isInstanceOf(InvalidRequestException::class.java)
    verify(validateTokenPort, never()).validate(any())
    verify(saveMenuPhotoPort, never()).save(any())
  }
}
