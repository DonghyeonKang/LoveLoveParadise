package com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.support.WebMvcTestBase
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.`in`.MenuSummary
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.`in`.MyMenuBoardRetrieved
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.`in`.RetrieveMyMenuBoardUseCase
import jakarta.servlet.http.Cookie
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(RetrieveMyMenuBoardWebAdapter::class)
class RetrieveMyMenuBoardWebAdapterTest : WebMvcTestBase() {

  @MockitoBean private lateinit var retrieveMyMenuBoardUseCase: RetrieveMyMenuBoardUseCase

  @Test
  @DisplayName("Scenario: 성공 - 인증된 사용자가 조회하면 shareSlug와 메뉴 목록을 반환한다")
  fun retrieve_my_menu_board_success() {
    // Given
    given(retrieveMyMenuBoardUseCase.operate(any()))
      .willReturn(
        MyMenuBoardRetrieved(
          "share-slug-uuid",
          listOf(MenuSummary("menu-uuid", "김치찌개", "얼큰한 김치찌개", null)),
        )
      )

    // When & Then
    mockMvc
      .perform(get("/api/v1/menus").cookie(Cookie("access_token", "valid.jwt.token")))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.shareSlug").value("share-slug-uuid"))
      .andExpect(jsonPath("$.items[0].name").value("김치찌개"))
  }

  @Test
  @DisplayName("Scenario: 실패 - access_token 쿠키가 없으면 401을 반환한다")
  fun retrieve_my_menu_board_fail_unauthorized() {
    mockMvc.perform(get("/api/v1/menus")).andExpect(status().isUnauthorized)
  }
}
