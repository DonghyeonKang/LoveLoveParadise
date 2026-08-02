package com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.service

import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.`in`.RetrieveFamilyMembersCmd
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.out.FamilyMember
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.out.LoadFamilyMembersPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class RetrieveFamilyMembersServiceTest {

  @Mock private lateinit var validateTokenPort: ValidateTokenPort
  @Mock private lateinit var loadUserFamilyPort: LoadUserFamilyPort
  @Mock private lateinit var loadFamilyMembersPort: LoadFamilyMembersPort

  private lateinit var service: RetrieveFamilyMembersService

  @BeforeEach
  fun setUp() {
    service = RetrieveFamilyMembersService(validateTokenPort, loadUserFamilyPort, loadFamilyMembersPort)
  }

  @Test
  @DisplayName("Scenario: 성공 - 토큰 검증 후 소속 가족의 구성원 목록을 반환한다")
  fun retrieve_family_members_success() {
    // Given
    val cmd = RetrieveFamilyMembersCmd("valid.jwt.token")
    whenever(validateTokenPort.validate(cmd.accessToken)).thenReturn("user-uuid")
    whenever(loadUserFamilyPort.load("user-uuid")).thenReturn("family-uuid")
    whenever(loadFamilyMembersPort.loadAll("family-uuid"))
      .thenReturn(listOf(FamilyMember("홍길동", "hong@test.com")))

    // When
    val result = service.operate(cmd)

    // Then
    assertThat(result.familyId).isEqualTo("family-uuid")
    assertThat(result.members).hasSize(1)
    assertThat(result.members[0].name).isEqualTo("홍길동")
    assertThat(result.members[0].email).isEqualTo("hong@test.com")
  }
}
