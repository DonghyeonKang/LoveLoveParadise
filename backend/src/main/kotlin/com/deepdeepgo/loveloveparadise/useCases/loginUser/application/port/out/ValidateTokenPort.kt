package com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out

interface ValidateTokenPort {
  fun validate(token: String): String
}
