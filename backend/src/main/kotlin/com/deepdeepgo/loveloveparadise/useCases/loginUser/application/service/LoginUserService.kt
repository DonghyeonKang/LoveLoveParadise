package com.deepdeepgo.loveloveparadise.useCases.loginUser.application.service

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.config.exception.UnauthorizedException
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.`in`.LoginUserCmd
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.`in`.LoginUserUseCase
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.`in`.UserLoggedIn
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.IssueTokenPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.LoadUserPort
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginUserService(
  private val loadUserPort: LoadUserPort,
  private val issueTokenPort: IssueTokenPort,
) : LoginUserUseCase {

  private val passwordEncoder = BCryptPasswordEncoder()

  override fun operate(cmd: LoginUserCmd): UserLoggedIn {
    val user =
      try {
        loadUserPort.loadByEmail(cmd.email)
      } catch (_: NotFoundException) {
        throw UnauthorizedException("인증 실패")
      }

    if (!passwordEncoder.matches(cmd.password, user.hashedPassword)) {
      throw UnauthorizedException("인증 실패")
    }

    return UserLoggedIn(issueTokenPort.issue(user.userId))
  }
}
