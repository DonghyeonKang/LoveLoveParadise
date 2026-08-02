package com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out

interface LoadUserFamilyPort {
  fun load(userId: String): String
}
