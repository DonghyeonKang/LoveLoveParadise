package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.MenuPhotoEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.MenuPhotoRepository
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.SaveMenuPhotoPort
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class UploadMenuPhotoPersistAdapter(
  private val menuPhotoRepository: MenuPhotoRepository,
) : SaveMenuPhotoPort {

  override fun save(familyId: String): String {
    val photo = MenuPhotoEntity(UUID.randomUUID().toString(), familyId)
    return menuPhotoRepository.save(photo).id
  }
}
