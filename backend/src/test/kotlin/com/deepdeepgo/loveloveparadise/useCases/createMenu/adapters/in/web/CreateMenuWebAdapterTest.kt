package com.deepdeepgo.loveloveparadise.useCases.createMenu.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.support.WebMvcTestBase
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.`in`.CreateMenuUseCase
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.`in`.MenuCreated
import jakarta.servlet.http.Cookie
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

@WebMvcTest(CreateMenuWebAdapter::class)
class CreateMenuWebAdapterTest : WebMvcTestBase() {

  @MockitoBean private lateinit var createMenuUseCase: CreateMenuUseCase

  @Test
  @DisplayName("Scenario: 성공 - 인증된 사용자가 메뉴 생성 요청 시 201과 menuId를 반환한다")
  fun create_menu_success() {
    // Given
    val req = CreateMenuRequest("김치찌개", "얼큰한 김치찌개", null)
    given(createMenuUseCase.operate(any())).willReturn(MenuCreated("menu-uuid"))

    // When & Then
    mockMvc
      .perform(
        post("/api/v1/menus")
          .cookie(Cookie("access_token", "valid.jwt.token"))
          .contentType(MediaType.APPLICATION_JSON)
          .content(json(req))
      )
      .andExpect(status().isCreated)
      .andExpect(jsonPath("$.menuId").value("menu-uuid"))

    verify(createMenuUseCase).operate(any())
  }

  @Test
  @DisplayName("Scenario: 실패 - access_token 쿠키가 없으면 401을 반환한다")
  fun create_menu_fail_unauthorized() {
    // Given
    val req = CreateMenuRequest("김치찌개", "얼큰한 김치찌개", null)

    // When & Then
    mockMvc
      .perform(
        post("/api/v1/menus")
          .contentType(MediaType.APPLICATION_JSON)
          .content(json(req))
      )
      .andExpect(status().isUnauthorized)
  }
}
