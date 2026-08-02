package com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.`in`

interface RetrieveMyMenuBoardUseCase {
  fun operate(cmd: RetrieveMyMenuBoardCmd): MyMenuBoardRetrieved
}
