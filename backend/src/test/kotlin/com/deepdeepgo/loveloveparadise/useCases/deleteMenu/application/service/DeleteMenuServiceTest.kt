package com.deepdeepgo.loveloveparadise.useCases.deleteMenu.application.service

import com.deepdeepgo.loveloveparadise.config.exception.ForbiddenException
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.deleteMenu.application.port.`in`.DeleteMenuCmd
import com.deepdeepgo.loveloveparadise.useCases.deleteMenu.application.port.out.DeleteMenuPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.LoadMenuPort
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.Menu
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class DeleteMenuServiceTest {

  @Mock private lateinit var validateTokenPort: ValidateTokenPort
  @Mock private lateinit var loadUserFamilyPort: LoadUserFamilyPort
  @Mock private lateinit var loadMenuPort: LoadMenuPort
  @Mock private lateinit var deleteMenuPort: DeleteMenuPort

  private lateinit var service: DeleteMenuService

  @BeforeEach
  fun setUp() {
    service = DeleteMenuService(validateTokenPort, loadUserFamilyPort, loadMenuPort, deleteMenuPort)
  }

  @Test
  @DisplayName("Scenario: 성공 - 본인 가족의 메뉴면 삭제한다")
  fun delete_menu_success() {
    // Given
    val cmd = DeleteMenuCmd("valid.jwt.token", "menu-uuid")
    whenever(validateTokenPort.validate(cmd.accessToken)).thenReturn("user-uuid")
    whenever(loadUserFamilyPort.load("user-uuid")).thenReturn("family-uuid")
    whenever(loadMenuPort.load("menu-uuid"))
      .thenReturn(Menu("menu-uuid", "family-uuid", "김치찌개", "얼큰한 김치찌개", null))

    // When
    service.operate(cmd)

    // Then
    verify(deleteMenuPort).delete("menu-uuid")
  }

  @Test
  @DisplayName("Scenario: 실패 - 다른 가족의 메뉴면 ForbiddenException을 던지고 삭제하지 않는다")
  fun delete_menu_fail_forbidden() {
    // Given
    val cmd = DeleteMenuCmd("valid.jwt.token", "menu-uuid")
    whenever(validateTokenPort.validate(cmd.accessToken)).thenReturn("user-uuid")
    whenever(loadUserFamilyPort.load("user-uuid")).thenReturn("my-family-uuid")
    whenever(loadMenuPort.load("menu-uuid"))
      .thenReturn(Menu("menu-uuid", "other-family-uuid", "김치찌개", "얼큰한 김치찌개", null))

    // When & Then
    assertThatThrownBy { service.operate(cmd) }.isInstanceOf(ForbiddenException::class.java)
    verify(deleteMenuPort, never()).delete("menu-uuid")
  }
}
