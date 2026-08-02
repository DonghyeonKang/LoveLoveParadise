package com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository

import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.FamilyEntity
import java.util.Optional
import org.springframework.data.jpa.repository.JpaRepository

interface FamilyRepository : JpaRepository<FamilyEntity, String> {
  fun findByShareSlug(shareSlug: String): Optional<FamilyEntity>
}
