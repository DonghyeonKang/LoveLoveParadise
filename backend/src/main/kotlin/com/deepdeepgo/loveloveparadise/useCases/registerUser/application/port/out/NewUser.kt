package com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out

data class NewUser(
  val email: String,
  val hashedPassword: String,
  val name: String,
  val familyId: String,
)
