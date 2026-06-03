package com.deepdeepgo.loveloveparadise.useCases.checkFamily.application.service

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.useCases.checkFamily.application.port.`in`.CheckFamilyCmd
import com.deepdeepgo.loveloveparadise.useCases.checkFamily.application.port.out.VerifyFamilyPort
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class CheckFamilyServiceTest {

  @Mock private lateinit var verifyFamilyPort: VerifyFamilyPort

  private lateinit var service: CheckFamilyService

  @BeforeEach
  fun setUp() {
    service = CheckFamilyService(verifyFamilyPort)
  }

  @Test
  @DisplayName("Scenario: 성공 - 존재하는 familyId로 요청 시 예외 없이 완료된다")
  fun check_family_success() {
    // Given
    doNothing().whenever(verifyFamilyPort).verify("valid-id")

    // When & Then
    assertThatCode { service.operate(CheckFamilyCmd("valid-id")) }.doesNotThrowAnyException()
    verify(verifyFamilyPort).verify("valid-id")
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 familyId로 요청 시 NotFoundException을 던진다")
  fun check_family_not_found() {
    // Given
    doThrow(NotFoundException("존재하지 않는 가족입니다.")).whenever(verifyFamilyPort).verify("invalid-id")

    // When & Then
    assertThatThrownBy { service.operate(CheckFamilyCmd("invalid-id")) }
      .isInstanceOf(NotFoundException::class.java)
  }
}
