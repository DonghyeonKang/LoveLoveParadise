package com.deepdeepgo.loveloveparadise.useCases.logoutUser.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.support.WebMvcTestBase
import com.deepdeepgo.loveloveparadise.useCases.logoutUser.application.port.`in`.LogoutUserUseCase
import com.deepdeepgo.loveloveparadise.useCases.logoutUser.application.port.`in`.UserLoggedOut
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(LogoutUserWebAdapter::class)
class LogoutUserWebAdapterTest : WebMvcTestBase() {

  @MockitoBean private lateinit var logoutUserUseCase: LogoutUserUseCase

  @Test
  @DisplayName("Scenario: 성공 - 로그아웃 시 유스케이스가 호출되고 access_token 쿠키가 만료된다")
  fun logout_success() {
    // Given
    given(logoutUserUseCase.operate(any())).willReturn(UserLoggedOut())

    // When & Then
    mockMvc
      .perform(post("/api/v1/auth/logout"))
      .andExpect(status().isOk)
      .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString("access_token=")))
      .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString("HttpOnly")))
      .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString("Max-Age=0")))

    verify(logoutUserUseCase).operate(any())
  }

  @Test
  @DisplayName("Scenario: 성공 - 쿠키 없이 요청해도 200을 반환한다")
  fun logout_success_without_cookie() {
    // Given
    given(logoutUserUseCase.operate(any())).willReturn(UserLoggedOut())

    // When & Then
    mockMvc
      .perform(post("/api/v1/auth/logout"))
      .andExpect(status().isOk)

    verify(logoutUserUseCase).operate(any())
  }
}
