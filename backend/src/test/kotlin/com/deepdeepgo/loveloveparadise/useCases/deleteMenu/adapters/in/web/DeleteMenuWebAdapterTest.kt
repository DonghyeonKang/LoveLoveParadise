package com.deepdeepgo.loveloveparadise.useCases.deleteMenu.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.ForbiddenException
import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.support.WebMvcTestBase
import com.deepdeepgo.loveloveparadise.useCases.deleteMenu.application.port.`in`.DeleteMenuUseCase
import jakarta.servlet.http.Cookie
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.willThrow
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(DeleteMenuWebAdapter::class)
class DeleteMenuWebAdapterTest : WebMvcTestBase() {

  @MockitoBean private lateinit var deleteMenuUseCase: DeleteMenuUseCase

  @Test
  @DisplayName("Scenario: 성공 - 인증된 사용자가 자신의 메뉴를 삭제하면 200을 반환한다")
  fun delete_menu_success() {
    // When & Then
    mockMvc
      .perform(delete("/api/v1/menus/menu-uuid").cookie(Cookie("access_token", "valid.jwt.token")))
      .andExpect(status().isOk)

    verify(deleteMenuUseCase).operate(any())
  }

  @Test
  @DisplayName("Scenario: 실패 - access_token 쿠키가 없으면 401을 반환한다")
  fun delete_menu_fail_unauthorized() {
    mockMvc.perform(delete("/api/v1/menus/menu-uuid")).andExpect(status().isUnauthorized)
  }

  @Test
  @DisplayName("Scenario: 실패 - 다른 가족의 메뉴를 삭제하려 하면 403을 반환한다")
  fun delete_menu_fail_forbidden() {
    // Given
    willThrow(ForbiddenException("다른 가족의 메뉴는 삭제할 수 없습니다."))
      .given(deleteMenuUseCase)
      .operate(any())

    // When & Then
    mockMvc
      .perform(delete("/api/v1/menus/menu-uuid").cookie(Cookie("access_token", "valid.jwt.token")))
      .andExpect(status().isForbidden)
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 메뉴를 삭제하려 하면 404를 반환한다")
  fun delete_menu_fail_not_found() {
    // Given
    willThrow(NotFoundException("존재하지 않는 메뉴입니다.")).given(deleteMenuUseCase).operate(any())

    // When & Then
    mockMvc
      .perform(delete("/api/v1/menus/menu-uuid").cookie(Cookie("access_token", "valid.jwt.token")))
      .andExpect(status().isNotFound)
  }
}
