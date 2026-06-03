package com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.`in`

interface RegisterUserUseCase {
  fun operate(cmd: RegisterUserCmd): UserRegistered
}
