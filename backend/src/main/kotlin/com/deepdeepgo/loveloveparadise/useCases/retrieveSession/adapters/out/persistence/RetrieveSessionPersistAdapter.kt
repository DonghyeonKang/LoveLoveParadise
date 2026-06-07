package com.deepdeepgo.loveloveparadise.useCases.retrieveSession.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.useCases.retrieveSession.application.port.out.LoadUserProfilePort
import com.deepdeepgo.loveloveparadise.useCases.retrieveSession.application.port.out.UserProfile
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class RetrieveSessionPersistAdapter(
  private val userRepository: UserRepository,
) : LoadUserProfilePort {

  override fun load(userId: String): UserProfile {
    val user =
      userRepository.findById(userId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
    return UserProfile(user.id, user.name, user.email)
  }
}
