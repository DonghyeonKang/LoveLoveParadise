package com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out

interface SaveUserPort {
  fun save(newUser: NewUser): String
}
