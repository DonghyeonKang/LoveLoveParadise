package com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out

data class NewMenu(
  val familyId: String,
  val name: String,
  val description: String,
  val photoId: String?,
)
