package com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.`in`

data class UpdateMenuCmd(
  val accessToken: String,
  val menuId: String,
  val name: String,
  val description: String,
  val photoId: String?,
)
