package com.deepdeepgo.loveloveparadise.useCases.registerUser.adapters.`in`.web

data class RegisterUserRequest(
  val email: String,
  val password: String,
  val name: String,
  val familyId: String?,
)
