package com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.support.WebMvcTestBase
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.`in`.FamilyMemberSummary
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.`in`.FamilyMembersRetrieved
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.`in`.RetrieveFamilyMembersUseCase
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

@WebMvcTest(RetrieveFamilyMembersWebAdapter::class)
class RetrieveFamilyMembersWebAdapterTest : WebMvcTestBase() {

  @MockitoBean private lateinit var retrieveFamilyMembersUseCase: RetrieveFamilyMembersUseCase

  @Test
  @DisplayName("Scenario: 성공 - 인증된 사용자가 조회하면 familyId와 구성원 목록을 반환한다")
  fun retrieve_family_members_success() {
    // Given
    given(retrieveFamilyMembersUseCase.operate(any()))
      .willReturn(
        FamilyMembersRetrieved("family-uuid", listOf(FamilyMemberSummary("홍길동", "hong@test.com")))
      )

    // When & Then
    mockMvc
      .perform(get("/api/v1/families/me").cookie(Cookie("access_token", "valid.jwt.token")))
      .andExpect(status().isOk)
      .andExpect(jsonPath("$.familyId").value("family-uuid"))
      .andExpect(jsonPath("$.members[0].name").value("홍길동"))
      .andExpect(jsonPath("$.members[0].email").value("hong@test.com"))
  }

  @Test
  @DisplayName("Scenario: 실패 - access_token 쿠키가 없으면 401을 반환한다")
  fun retrieve_family_members_fail_unauthorized() {
    mockMvc.perform(get("/api/v1/families/me")).andExpect(status().isUnauthorized)
  }
}
