package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.support.WebMvcTestBase
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.`in`.MenuPhotoUploaded
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.`in`.UploadMenuPhotoUseCase
import jakarta.servlet.http.Cookie
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UploadMenuPhotoWebAdapter::class)
class UploadMenuPhotoWebAdapterTest : WebMvcTestBase() {

  @MockitoBean private lateinit var uploadMenuPhotoUseCase: UploadMenuPhotoUseCase

  @Test
  @DisplayName("Scenario: 성공 - 인증된 사용자가 이미지를 업로드하면 201과 photoId를 반환한다")
  fun upload_photo_success() {
    // Given
    val file = MockMultipartFile("file", "menu.jpg", "image/jpeg", byteArrayOf(1, 2, 3))
    given(uploadMenuPhotoUseCase.operate(any())).willReturn(MenuPhotoUploaded("photo-uuid"))

    // When & Then
    mockMvc
      .perform(
        multipart("/api/v1/menus/photos").file(file).cookie(Cookie("access_token", "valid.jwt.token"))
      )
      .andExpect(status().isCreated)
      .andExpect(jsonPath("$.photoId").value("photo-uuid"))

    verify(uploadMenuPhotoUseCase).operate(any())
  }

  @Test
  @DisplayName("Scenario: 실패 - access_token 쿠키가 없으면 401을 반환한다")
  fun upload_photo_fail_unauthorized() {
    // Given
    val file = MockMultipartFile("file", "menu.jpg", "image/jpeg", byteArrayOf(1, 2, 3))

    // When & Then
    mockMvc.perform(multipart("/api/v1/menus/photos").file(file)).andExpect(status().isUnauthorized)
  }
}
