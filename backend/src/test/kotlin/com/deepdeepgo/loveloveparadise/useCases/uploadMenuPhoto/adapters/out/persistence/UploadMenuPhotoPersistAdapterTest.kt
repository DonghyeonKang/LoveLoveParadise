package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.support.DataJpaTestBase
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.MenuPhotoRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(UploadMenuPhotoPersistAdapter::class)
class UploadMenuPhotoPersistAdapterTest : DataJpaTestBase() {

  @Autowired private lateinit var adapter: UploadMenuPhotoPersistAdapter
  @Autowired private lateinit var menuPhotoRepository: MenuPhotoRepository

  @Test
  @DisplayName("Scenario: 성공 - 사진 메타데이터를 저장하고 photoId를 반환한다")
  fun save_photo_returns_id() {
    // When
    val photoId = adapter.save("family-uuid")

    // Then
    assertThat(photoId).isNotBlank()
    val saved = menuPhotoRepository.findById(photoId)
    assertThat(saved).isPresent
    assertThat(saved.get().familyId).isEqualTo("family-uuid")
  }
}
