package com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository

import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, String> {
  fun existsByEmail(email: String): Boolean
  fun findByEmail(email: String): java.util.Optional<UserEntity>
  fun findAllByFamilyId(familyId: String): List<UserEntity>
}
