package com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.service

import com.deepdeepgo.loveloveparadise.config.exception.ForbiddenException
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.`in`.UpdateMenuCmd
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.LoadMenuPort
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.Menu
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.UpdateMenuPort
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
class UpdateMenuServiceTest {

  @Mock private lateinit var validateTokenPort: ValidateTokenPort
  @Mock private lateinit var loadUserFamilyPort: LoadUserFamilyPort
  @Mock private lateinit var loadMenuPort: LoadMenuPort
  @Mock private lateinit var updateMenuPort: UpdateMenuPort

  private lateinit var service: UpdateMenuService

  @BeforeEach
  fun setUp() {
    service = UpdateMenuService(validateTokenPort, loadUserFamilyPort, loadMenuPort, updateMenuPort)
  }

  @Test
  @DisplayName("Scenario: 성공 - 본인 가족의 메뉴면 수정한다")
  fun update_menu_success() {
    // Given
    val cmd = UpdateMenuCmd("valid.jwt.token", "menu-uuid", "김치찌개", "더 얼큰하게", null)
    whenever(validateTokenPort.validate(cmd.accessToken)).thenReturn("user-uuid")
    whenever(loadUserFamilyPort.load("user-uuid")).thenReturn("family-uuid")
    whenever(loadMenuPort.load("menu-uuid"))
      .thenReturn(Menu("menu-uuid", "family-uuid", "old-name", "old-desc", null))

    // When
    val result = service.operate(cmd)

    // Then
    assertThat(result.menuId).isEqualTo("menu-uuid")
    verify(updateMenuPort).update("menu-uuid", "김치찌개", "더 얼큰하게", null)
  }

  @Test
  @DisplayName("Scenario: 실패 - 다른 가족의 메뉴면 ForbiddenException을 던지고 수정하지 않는다")
  fun update_menu_fail_forbidden() {
    // Given
    val cmd = UpdateMenuCmd("valid.jwt.token", "menu-uuid", "김치찌개", "더 얼큰하게", null)
    whenever(validateTokenPort.validate(cmd.accessToken)).thenReturn("user-uuid")
    whenever(loadUserFamilyPort.load("user-uuid")).thenReturn("my-family-uuid")
    whenever(loadMenuPort.load("menu-uuid"))
      .thenReturn(Menu("menu-uuid", "other-family-uuid", "old-name", "old-desc", null))

    // When & Then
    assertThatThrownBy { service.operate(cmd) }.isInstanceOf(ForbiddenException::class.java)
    verify(updateMenuPort, never()).update(any(), any(), any(), any())
  }
}
