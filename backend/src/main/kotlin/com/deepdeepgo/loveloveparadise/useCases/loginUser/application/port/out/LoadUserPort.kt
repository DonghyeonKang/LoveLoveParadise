package com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out

interface LoadUserPort {
  fun loadByEmail(email: String): LoadedUser
}
