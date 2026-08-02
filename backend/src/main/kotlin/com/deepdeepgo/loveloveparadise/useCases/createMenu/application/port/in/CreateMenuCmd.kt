package com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.`in`

data class CreateMenuCmd(
  val accessToken: String,
  val name: String,
  val description: String,
  val photoId: String?,
)
