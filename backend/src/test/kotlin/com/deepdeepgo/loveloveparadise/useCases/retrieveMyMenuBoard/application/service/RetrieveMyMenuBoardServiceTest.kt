package com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.service

import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.`in`.RetrieveMyMenuBoardCmd
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.out.LoadFamilyShareSlugPort
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.out.LoadMenusByFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.Menu
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class RetrieveMyMenuBoardServiceTest {

  @Mock private lateinit var validateTokenPort: ValidateTokenPort
  @Mock private lateinit var loadUserFamilyPort: LoadUserFamilyPort
  @Mock private lateinit var loadFamilyShareSlugPort: LoadFamilyShareSlugPort
  @Mock private lateinit var loadMenusByFamilyPort: LoadMenusByFamilyPort

  private lateinit var service: RetrieveMyMenuBoardService

  @BeforeEach
  fun setUp() {
    service =
      RetrieveMyMenuBoardService(
        validateTokenPort,
        loadUserFamilyPort,
        loadFamilyShareSlugPort,
        loadMenusByFamilyPort,
      )
  }

  @Test
  @DisplayName("Scenario: 성공 - 토큰 검증 후 가족의 shareSlug와 메뉴 목록을 반환한다")
  fun retrieve_my_menu_board_success() {
    // Given
    val cmd = RetrieveMyMenuBoardCmd("valid.jwt.token")
    whenever(validateTokenPort.validate(cmd.accessToken)).thenReturn("user-uuid")
    whenever(loadUserFamilyPort.load("user-uuid")).thenReturn("family-uuid")
    whenever(loadFamilyShareSlugPort.load("family-uuid")).thenReturn("share-slug-uuid")
    whenever(loadMenusByFamilyPort.loadAll("family-uuid"))
      .thenReturn(listOf(Menu("menu-uuid", "family-uuid", "김치찌개", "얼큰한 김치찌개", null)))

    // When
    val result = service.operate(cmd)

    // Then
    assertThat(result.shareSlug).isEqualTo("share-slug-uuid")
    assertThat(result.items).hasSize(1)
    assertThat(result.items[0].name).isEqualTo("김치찌개")
  }
}
