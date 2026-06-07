package com.deepdeepgo.loveloveparadise.useCases.retrieveSession.application.port.out

data class UserProfile(
  val userId: String,
  val name: String,
  val email: String,
)

interface LoadUserProfilePort {
  fun load(userId: String): UserProfile
}
