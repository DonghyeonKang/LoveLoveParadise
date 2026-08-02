package com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.`in`

interface ViewPublicMenuBoardUseCase {
  fun operate(cmd: ViewPublicMenuBoardCmd): PublicMenuBoardRetrieved
}
