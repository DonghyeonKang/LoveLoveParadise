package com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.`in`

data class RegisterUserCmd(
  val email: String,
  val password: String,
  val name: String,
  val familyId: String?,
)
