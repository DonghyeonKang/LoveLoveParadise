package com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out

interface CheckEmailPort {
  fun existsByEmail(email: String): Boolean
}
