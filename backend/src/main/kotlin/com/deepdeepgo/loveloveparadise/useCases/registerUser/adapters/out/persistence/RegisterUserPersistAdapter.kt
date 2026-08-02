package com.deepdeepgo.loveloveparadise.useCases.registerUser.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.CheckEmailPort
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.LoadFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.NewUser
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.SaveFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.SaveUserPort
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.FamilyEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.UserEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.FamilyRepository
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.UserRepository
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class RegisterUserPersistAdapter(
  private val userRepository: UserRepository,
  private val familyRepository: FamilyRepository,
) : CheckEmailPort, LoadFamilyPort, SaveFamilyPort, SaveUserPort {

  override fun existsByEmail(email: String): Boolean = userRepository.existsByEmail(email)

  override fun load(familyId: String): String {
    familyRepository.findById(familyId).orElseThrow { NotFoundException("존재하지 않는 가족입니다.") }
    return familyId
  }

  override fun save(): String {
    val family = FamilyEntity(UUID.randomUUID().toString(), UUID.randomUUID().toString())
    return familyRepository.save(family).id
  }

  override fun save(newUser: NewUser): String {
    val user =
      UserEntity(
        id = UUID.randomUUID().toString(),
        email = newUser.email,
        password = newUser.hashedPassword,
        name = newUser.name,
        familyId = newUser.familyId,
      )
    return userRepository.save(user).id
  }
}
