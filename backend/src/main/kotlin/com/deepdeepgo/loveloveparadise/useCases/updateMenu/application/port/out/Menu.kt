package com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out

data class Menu(
  val id: String,
  val familyId: String,
  val name: String,
  val description: String,
  val photoId: String?,
)
