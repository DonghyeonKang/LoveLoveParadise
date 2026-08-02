package com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.`in`

interface CreateMenuUseCase {
  fun operate(cmd: CreateMenuCmd): MenuCreated
}
