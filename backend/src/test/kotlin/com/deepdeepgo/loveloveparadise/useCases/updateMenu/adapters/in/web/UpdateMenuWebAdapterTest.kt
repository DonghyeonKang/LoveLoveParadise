package com.deepdeepgo.loveloveparadise.useCases.updateMenu.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.ForbiddenException
import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.support.WebMvcTestBase
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.`in`.MenuUpdated
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.`in`.UpdateMenuUseCase
import jakarta.servlet.http.Cookie
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(UpdateMenuWebAdapter::class)
class UpdateMenuWebAdapterTest : WebMvcTestBase() {

  @MockitoBean private lateinit var updateMenuUseCase: UpdateMenuUseCase

  @Test
  @DisplayName("Scenario: 성공 - 인증된 사용자가 자신의 메뉴를 수정하면 200을 반환한다")
  fun update_menu_success() {
    // Given
    val req = UpdateMenuRequest("김치찌개", "더 얼큰하게", null)
    given(updateMenuUseCase.operate(any())).willReturn(MenuUpdated("menu-uuid"))

    // When & Then
    mockMvc
      .perform(
        put("/api/v1/menus/menu-uuid")
          .cookie(Cookie("access_token", "valid.jwt.token"))
          .contentType(MediaType.APPLICATION_JSON)
          .content(json(req))
      )
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.menuId").value("menu-uuid"))
  }

  @Test
  @DisplayName("Scenario: 실패 - access_token 쿠키가 없으면 401을 반환한다")
  fun update_menu_fail_unauthorized() {
    // Given
    val req = UpdateMenuRequest("김치찌개", "더 얼큰하게", null)

    // When & Then
    mockMvc
      .perform(
        put("/api/v1/menus/menu-uuid").contentType(MediaType.APPLICATION_JSON).content(json(req))
      )
      .andExpect(status().isUnauthorized)
  }

  @Test
  @DisplayName("Scenario: 실패 - 다른 가족의 메뉴를 수정하려 하면 403을 반환한다")
  fun update_menu_fail_forbidden() {
    // Given
    val req = UpdateMenuRequest("김치찌개", "더 얼큰하게", null)
    given(updateMenuUseCase.operate(any()))
      .willThrow(ForbiddenException("다른 가족의 메뉴는 수정할 수 없습니다."))

    // When & Then
    mockMvc
      .perform(
        put("/api/v1/menus/menu-uuid")
          .cookie(Cookie("access_token", "valid.jwt.token"))
          .contentType(MediaType.APPLICATION_JSON)
          .content(json(req))
      )
      .andExpect(status().isForbidden)
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 메뉴를 수정하려 하면 404를 반환한다")
  fun update_menu_fail_not_found() {
    // Given
    val req = UpdateMenuRequest("김치찌개", "더 얼큰하게", null)
    given(updateMenuUseCase.operate(any())).willThrow(NotFoundException("존재하지 않는 메뉴입니다."))

    // When & Then
    mockMvc
      .perform(
        put("/api/v1/menus/menu-uuid")
          .cookie(Cookie("access_token", "valid.jwt.token"))
          .contentType(MediaType.APPLICATION_JSON)
          .content(json(req))
      )
      .andExpect(status().isNotFound)
  }
}
