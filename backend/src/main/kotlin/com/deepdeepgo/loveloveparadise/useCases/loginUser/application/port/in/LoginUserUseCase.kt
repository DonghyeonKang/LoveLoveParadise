package com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.`in`

interface LoginUserUseCase {
  fun operate(cmd: LoginUserCmd): UserLoggedIn
}
