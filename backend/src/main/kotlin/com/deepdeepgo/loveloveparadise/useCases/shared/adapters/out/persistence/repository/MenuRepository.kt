package com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository

import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.MenuEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MenuRepository : JpaRepository<MenuEntity, String> {
  fun findAllByFamilyId(familyId: String): List<MenuEntity>
}
