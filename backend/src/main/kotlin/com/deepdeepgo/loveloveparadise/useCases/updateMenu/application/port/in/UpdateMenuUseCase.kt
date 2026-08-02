package com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.`in`

interface UpdateMenuUseCase {
  fun operate(cmd: UpdateMenuCmd): MenuUpdated
}
