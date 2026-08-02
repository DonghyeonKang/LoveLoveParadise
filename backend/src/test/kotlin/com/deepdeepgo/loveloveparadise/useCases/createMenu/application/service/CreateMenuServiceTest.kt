package com.deepdeepgo.loveloveparadise.useCases.createMenu.application.service

import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.`in`.CreateMenuCmd
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.SaveMenuPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class CreateMenuServiceTest {

  @Mock private lateinit var validateTokenPort: ValidateTokenPort
  @Mock private lateinit var loadUserFamilyPort: LoadUserFamilyPort
  @Mock private lateinit var saveMenuPort: SaveMenuPort

  private lateinit var service: CreateMenuService

  @BeforeEach
  fun setUp() {
    service = CreateMenuService(validateTokenPort, loadUserFamilyPort, saveMenuPort)
  }

  @Test
  @DisplayName("Scenario: 성공 - 토큰 검증 후 사용자의 가족으로 메뉴를 생성한다")
  fun create_menu_success() {
    // Given
    val cmd = CreateMenuCmd("valid.jwt.token", "김치찌개", "얼큰한 김치찌개", null)
    whenever(validateTokenPort.validate(cmd.accessToken)).thenReturn("user-uuid")
    whenever(loadUserFamilyPort.load("user-uuid")).thenReturn("family-uuid")
    whenever(saveMenuPort.save(any())).thenReturn("menu-uuid")

    // When
    val result = service.operate(cmd)

    // Then
    assertThat(result.menuId).isEqualTo("menu-uuid")
    verify(saveMenuPort).save(any())
  }
}
