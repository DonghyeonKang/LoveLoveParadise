package com.deepdeepgo.loveloveparadise.useCases.logoutUser.application.service

import com.deepdeepgo.loveloveparadise.useCases.logoutUser.application.port.`in`.LogoutUserCmd
import com.deepdeepgo.loveloveparadise.useCases.logoutUser.application.port.`in`.LogoutUserUseCase
import com.deepdeepgo.loveloveparadise.useCases.logoutUser.application.port.`in`.UserLoggedOut
import org.springframework.stereotype.Service

@Service
class LogoutUserService : LogoutUserUseCase {

  override fun operate(cmd: LogoutUserCmd): UserLoggedOut = UserLoggedOut()
}
