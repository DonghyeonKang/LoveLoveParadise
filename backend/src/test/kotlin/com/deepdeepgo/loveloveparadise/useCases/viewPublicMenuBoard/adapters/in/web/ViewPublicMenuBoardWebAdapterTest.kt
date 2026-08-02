package com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.support.WebMvcTestBase
import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.`in`.PublicMenuBoardRetrieved
import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.`in`.PublicMenuItem
import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.`in`.ViewPublicMenuBoardUseCase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.kotlin.any
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ViewPublicMenuBoardWebAdapter::class)
class ViewPublicMenuBoardWebAdapterTest : WebMvcTestBase() {

  @MockitoBean private lateinit var viewPublicMenuBoardUseCase: ViewPublicMenuBoardUseCase

  @Test
  @DisplayName("Scenario: 성공 - 유효한 공유 슬러그로 조회하면 인증 없이 200과 메뉴 목록을 반환한다")
  fun view_public_menu_board_success() {
    // Given
    given(viewPublicMenuBoardUseCase.operate(any()))
      .willReturn(
        PublicMenuBoardRetrieved(listOf(PublicMenuItem("menu-uuid", "김치찌개", "얼큰한 김치찌개", null)))
      )

    // When & Then
    mockMvc
      .perform(get("/api/v1/menu-boards/share-slug-uuid"))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.items[0].name").value("김치찌개"))
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 공유 슬러그면 404를 반환한다")
  fun view_public_menu_board_fail_not_found() {
    // Given
    given(viewPublicMenuBoardUseCase.operate(any()))
      .willThrow(NotFoundException("존재하지 않는 메뉴판입니다."))

    // When & Then
    mockMvc.perform(get("/api/v1/menu-boards/unknown-slug")).andExpect(status().isNotFound)
  }
}
