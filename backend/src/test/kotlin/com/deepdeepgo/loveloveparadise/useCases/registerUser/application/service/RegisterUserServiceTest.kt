package com.deepdeepgo.loveloveparadise.useCases.registerUser.application.service

import com.deepdeepgo.loveloveparadise.config.exception.ConflictException
import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.`in`.RegisterUserCmd
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.CheckEmailPort
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.LoadFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.SaveFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.SaveUserPort
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class RegisterUserServiceTest {

  @Mock private lateinit var checkEmailPort: CheckEmailPort
  @Mock private lateinit var loadFamilyPort: LoadFamilyPort
  @Mock private lateinit var saveFamilyPort: SaveFamilyPort
  @Mock private lateinit var saveUserPort: SaveUserPort

  private lateinit var service: RegisterUserService

  @BeforeEach
  fun setUp() {
    service = RegisterUserService(checkEmailPort, loadFamilyPort, saveFamilyPort, saveUserPort)
  }

  @Test
  @DisplayName("Scenario: 성공 - familyId 없이 요청 시 새 가족을 생성하고 사용자를 저장한다")
  fun register_success_create_new_family() {
    // Given
    val cmd = RegisterUserCmd("user@test.com", "pass1234", "홍길동", null)
    whenever(checkEmailPort.existsByEmail(cmd.email)).thenReturn(false)
    whenever(saveFamilyPort.save()).thenReturn("new-family-uuid")
    whenever(saveUserPort.save(any())).thenReturn("user-uuid")

    // When
    val result = service.operate(cmd)

    // Then
    assertThat(result.userId).isEqualTo("user-uuid")
    assertThat(result.familyId).isEqualTo("new-family-uuid")
    verify(saveFamilyPort).save()
    verify(loadFamilyPort, never()).load(any())
  }

  @Test
  @DisplayName("Scenario: 성공 - 기존 familyId로 요청 시 가족을 조회하고 사용자를 저장한다")
  fun register_success_join_existing_family() {
    // Given
    val cmd = RegisterUserCmd("user2@test.com", "pass1234", "김철수", "existing-family-uuid")
    whenever(checkEmailPort.existsByEmail(cmd.email)).thenReturn(false)
    whenever(loadFamilyPort.load("existing-family-uuid")).thenReturn("existing-family-uuid")
    whenever(saveUserPort.save(any())).thenReturn("user2-uuid")

    // When
    val result = service.operate(cmd)

    // Then
    assertThat(result.userId).isEqualTo("user2-uuid")
    assertThat(result.familyId).isEqualTo("existing-family-uuid")
    verify(saveFamilyPort, never()).save()
    verify(loadFamilyPort).load("existing-family-uuid")
  }

  @Test
  @DisplayName("Scenario: 실패 - 이메일 중복 시 ConflictException을 던진다")
  fun register_fail_conflict_email() {
    // Given
    val cmd = RegisterUserCmd("duplicate@test.com", "pass1234", "홍길동", null)
    whenever(checkEmailPort.existsByEmail(cmd.email)).thenReturn(true)

    // When & Then
    assertThatThrownBy { service.operate(cmd) }.isInstanceOf(ConflictException::class.java)
    verify(saveFamilyPort, never()).save()
    verify(saveUserPort, never()).save(any())
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 familyId로 요청 시 NotFoundException을 던진다")
  fun register_fail_family_not_found() {
    // Given
    val cmd = RegisterUserCmd("user3@test.com", "pass1234", "이영희", "unknown-uuid")
    whenever(checkEmailPort.existsByEmail(cmd.email)).thenReturn(false)
    whenever(loadFamilyPort.load("unknown-uuid")).thenThrow(NotFoundException("존재하지 않는 가족입니다."))

    // When & Then
    assertThatThrownBy { service.operate(cmd) }.isInstanceOf(NotFoundException::class.java)
    verify(saveUserPort, never()).save(any())
  }
}
