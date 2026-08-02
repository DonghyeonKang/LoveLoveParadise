package com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out

interface LoadMenuPort {
  fun load(menuId: String): Menu
}
