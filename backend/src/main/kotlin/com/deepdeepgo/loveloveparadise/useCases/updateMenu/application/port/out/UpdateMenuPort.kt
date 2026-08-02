package com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out

interface UpdateMenuPort {
  fun update(menuId: String, name: String, description: String, photoId: String?)
}
