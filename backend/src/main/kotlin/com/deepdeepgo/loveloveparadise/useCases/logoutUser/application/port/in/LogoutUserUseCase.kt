package com.deepdeepgo.loveloveparadise.useCases.logoutUser.application.port.`in`

interface LogoutUserUseCase {
  fun operate(cmd: LogoutUserCmd): UserLoggedOut
}
