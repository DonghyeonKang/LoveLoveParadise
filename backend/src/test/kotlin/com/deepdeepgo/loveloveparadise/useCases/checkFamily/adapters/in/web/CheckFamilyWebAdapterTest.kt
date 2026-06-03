package com.deepdeepgo.loveloveparadise.useCases.checkFamily.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.support.WebMvcTestBase
import com.deepdeepgo.loveloveparadise.useCases.checkFamily.application.port.`in`.CheckFamilyUseCase
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CheckFamilyWebAdapter::class)
class CheckFamilyWebAdapterTest : WebMvcTestBase() {

  @MockitoBean private lateinit var checkFamilyUseCase: CheckFamilyUseCase

  @Test
  @DisplayName("Scenario: 성공 - 존재하는 familyId로 요청 시 200을 반환한다")
  fun check_family_success() {
    // Given - Unit 반환 메서드는 별도 stubbing 없이 기본적으로 아무것도 하지 않음

    // When & Then
    mockMvc
      .perform(get("/api/v1/families/valid-family-id"))
      .andExpect(status().isOk)

    verify(checkFamilyUseCase).operate(any())
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 familyId로 요청 시 404를 반환한다")
  fun check_family_not_found() {
    // Given
    whenever(checkFamilyUseCase.operate(any())).thenThrow(NotFoundException("존재하지 않는 가족입니다."))

    // When & Then
    mockMvc
      .perform(get("/api/v1/families/invalid-family-id"))
      .andExpect(status().isNotFound)
  }
}
