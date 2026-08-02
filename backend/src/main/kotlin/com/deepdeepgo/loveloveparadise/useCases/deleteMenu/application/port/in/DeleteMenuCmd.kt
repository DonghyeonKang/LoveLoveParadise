package com.deepdeepgo.loveloveparadise.useCases.deleteMenu.application.port.`in`

data class DeleteMenuCmd(
  val accessToken: String,
  val menuId: String,
)
