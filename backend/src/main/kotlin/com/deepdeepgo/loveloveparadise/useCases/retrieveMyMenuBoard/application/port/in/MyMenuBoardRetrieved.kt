package com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.`in`

data class MyMenuBoardRetrieved(
  val shareSlug: String,
  val items: List<MenuSummary>,
)
