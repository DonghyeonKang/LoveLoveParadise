package com.deepdeepgo.loveloveparadise.useCases.loginUser.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.LoadUserPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.LoadedUser
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class LoginUserPersistAdapter(
  private val userRepository: UserRepository,
) : LoadUserPort {

  override fun loadByEmail(email: String): LoadedUser {
    val user =
      userRepository.findByEmail(email).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
    return LoadedUser(user.id, user.password)
  }
}
