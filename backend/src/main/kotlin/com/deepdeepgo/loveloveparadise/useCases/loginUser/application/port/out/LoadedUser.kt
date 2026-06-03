package com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out

data class LoadedUser(
  val userId: String,
  val hashedPassword: String,
)
