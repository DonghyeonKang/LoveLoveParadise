package com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository

import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.MenuPhotoEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MenuPhotoRepository : JpaRepository<MenuPhotoEntity, String> {
  fun existsByIdAndFamilyId(id: String, familyId: String): Boolean
}
