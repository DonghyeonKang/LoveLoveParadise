package com.deepdeepgo.loveloveparadise.useCases.registerUser.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.ConflictException
import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.support.WebMvcTestBase
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.`in`.RegisterUserUseCase
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.`in`.UserRegistered
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RegisterUserWebAdapter::class)
class RegisterUserWebAdapterTest : WebMvcTestBase() {

  @MockitoBean private lateinit var registerUserUseCase: RegisterUserUseCase

  @Test
  @DisplayName("Scenario: 성공 - familyId 없이 요청 시 새 가족을 생성하고 201을 반환한다")
  fun register_success_create_new_family() {
    // Given
    val req = RegisterUserRequest("user@test.com", "pass1234", "홍길동", null)
    given(registerUserUseCase.operate(any()))
      .willReturn(UserRegistered("user-uuid", "new-family-uuid"))

    // When & Then
    mockMvc
      .perform(
        post("/api/v1/auth/register")
          .contentType(MediaType.APPLICATION_JSON)
          .content(json(req))
      )
      .andExpect(status().isCreated)
      .andExpect(jsonPath("$.userId").value("user-uuid"))
      .andExpect(jsonPath("$.familyId").value("new-family-uuid"))

    verify(registerUserUseCase).operate(any())
  }

  @Test
  @DisplayName("Scenario: 성공 - 기존 familyId로 요청 시 해당 가족에 합류하고 201을 반환한다")
  fun register_success_join_existing_family() {
    // Given
    val req = RegisterUserRequest("user2@test.com", "pass1234", "김철수", "existing-family-uuid")
    given(registerUserUseCase.operate(any()))
      .willReturn(UserRegistered("user2-uuid", "existing-family-uuid"))

    // When & Then
    mockMvc
      .perform(
        post("/api/v1/auth/register")
          .contentType(MediaType.APPLICATION_JSON)
          .content(json(req))
      )
      .andExpect(status().isCreated)
      .andExpect(jsonPath("$.userId").value("user2-uuid"))
      .andExpect(jsonPath("$.familyId").value("existing-family-uuid"))

    verify(registerUserUseCase).operate(any())
  }

  @Test
  @DisplayName("Scenario: 실패 - 이메일 중복 시 409를 반환한다")
  fun register_fail_conflict_email() {
    // Given
    val req = RegisterUserRequest("duplicate@test.com", "pass1234", "홍길동", null)
    given(registerUserUseCase.operate(any())).willThrow(ConflictException("이미 사용 중인 이메일입니다."))

    // When & Then
    mockMvc
      .perform(
        post("/api/v1/auth/register")
          .contentType(MediaType.APPLICATION_JSON)
          .content(json(req))
      )
      .andExpect(status().isConflict)
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 familyId로 요청 시 404를 반환한다")
  fun register_fail_family_not_found() {
    // Given
    val req = RegisterUserRequest("user3@test.com", "pass1234", "이영희", "unknown-family-uuid")
    given(registerUserUseCase.operate(any())).willThrow(NotFoundException("존재하지 않는 가족입니다."))

    // When & Then
    mockMvc
      .perform(
        post("/api/v1/auth/register")
          .contentType(MediaType.APPLICATION_JSON)
          .content(json(req))
      )
      .andExpect(status().isNotFound)
  }
}
