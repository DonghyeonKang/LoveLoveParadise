package com.deepdeepgo.loveloveparadise.useCases.logoutUser.application.service

import com.deepdeepgo.loveloveparadise.useCases.logoutUser.application.port.`in`.LogoutUserCmd
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class LogoutUserServiceTest {

  private val service = LogoutUserService()

  @Test
  @DisplayName("Scenario: 성공 - 로그아웃 요청 시 UserLoggedOut을 반환한다")
  fun logout_success() {
    // When
    val result = service.operate(LogoutUserCmd())

    // Then
    assertThat(result).isInstanceOf(com.deepdeepgo.loveloveparadise.useCases.logoutUser.application.port.`in`.UserLoggedOut::class.java)
  }
}
