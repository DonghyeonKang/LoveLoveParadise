package com.deepdeepgo.loveloveparadise.useCases.loginUser.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.UnauthorizedException
import com.deepdeepgo.loveloveparadise.support.WebMvcTestBase
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.`in`.LoginUserUseCase
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.`in`.UserLoggedIn
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(LoginUserWebAdapter::class)
class LoginUserWebAdapterTest : WebMvcTestBase() {

  @MockitoBean private lateinit var loginUserUseCase: LoginUserUseCase

  @Test
  @DisplayName("Scenario: 성공 - 유효한 요청 시 로그인 유스케이스가 호출되고 HttpOnly 쿠키가 설정된다")
  fun login_success() {
    // Given
    val req = LoginUserRequest("test@test.com", "password123")
    given(loginUserUseCase.operate(any())).willReturn(UserLoggedIn("sample.jwt.token"))

    // When
    mockMvc
      .perform(
        post("/api/v1/auth/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(json(req))
      )
      .andExpect(status().isOk)
      .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString("access_token=")))
      .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString("HttpOnly")))
      .andExpect(header().string(HttpHeaders.SET_COOKIE, containsString("Max-Age=1800")))

    // Then
    verify(loginUserUseCase).operate(any())
  }

  @Test
  @DisplayName("Scenario: 실패 - 인증 실패 시 401이 반환된다")
  fun login_fail_unauthorized() {
    // Given
    val req = LoginUserRequest("wrong@test.com", "wrongpassword")
    given(loginUserUseCase.operate(any())).willThrow(UnauthorizedException("인증 실패"))

    // When & Then
    mockMvc
      .perform(
        post("/api/v1/auth/login")
          .contentType(MediaType.APPLICATION_JSON)
          .content(json(req))
      )
      .andExpect(status().isUnauthorized)
  }
}
