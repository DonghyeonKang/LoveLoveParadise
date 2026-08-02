package com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out

interface SaveMenuPort {
  fun save(newMenu: NewMenu): String
}
