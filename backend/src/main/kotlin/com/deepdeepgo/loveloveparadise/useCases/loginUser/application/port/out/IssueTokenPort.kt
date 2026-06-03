package com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out

interface IssueTokenPort {
  fun issue(userId: String): String
}
