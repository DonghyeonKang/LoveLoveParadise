package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.adapters.out.storage

import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.PhotoVariants
import java.nio.file.Files
import java.nio.file.Path
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir

class StoreMenuPhotoLocalDiskAdapterTest {

  @Test
  @DisplayName("Scenario: 성공 - photoId 디렉터리 아래에 원본/중간/썸네일 3개 파일을 저장한다")
  fun store_writes_three_files(
    @TempDir tempDir: Path
  ) {
    // Given
    val adapter = StoreMenuPhotoLocalDiskAdapter(tempDir.toString())
    val variants = PhotoVariants(byteArrayOf(1), byteArrayOf(2, 2), byteArrayOf(3, 3, 3))

    // When
    adapter.store("photo-uuid", variants)

    // Then
    val photoDir = tempDir.resolve("photo-uuid")
    assertThat(Files.readAllBytes(photoDir.resolve("original.jpg"))).isEqualTo(byteArrayOf(1))
    assertThat(Files.readAllBytes(photoDir.resolve("medium.jpg"))).isEqualTo(byteArrayOf(2, 2))
    assertThat(Files.readAllBytes(photoDir.resolve("thumb.jpg"))).isEqualTo(byteArrayOf(3, 3, 3))
  }
}
