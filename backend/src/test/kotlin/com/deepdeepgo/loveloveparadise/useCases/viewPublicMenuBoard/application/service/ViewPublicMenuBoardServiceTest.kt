package com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.service

import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.out.LoadMenusByFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.Menu
import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.`in`.ViewPublicMenuBoardCmd
import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.out.LoadFamilyByShareSlugPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class ViewPublicMenuBoardServiceTest {

  @Mock private lateinit var loadFamilyByShareSlugPort: LoadFamilyByShareSlugPort
  @Mock private lateinit var loadMenusByFamilyPort: LoadMenusByFamilyPort

  private lateinit var service: ViewPublicMenuBoardService

  @BeforeEach
  fun setUp() {
    service = ViewPublicMenuBoardService(loadFamilyByShareSlugPort, loadMenusByFamilyPort)
  }

  @Test
  @DisplayName("Scenario: 성공 - 공유 슬러그로 가족을 찾아 메뉴 목록을 반환한다")
  fun view_public_menu_board_success() {
    // Given
    val cmd = ViewPublicMenuBoardCmd("share-slug-uuid")
    whenever(loadFamilyByShareSlugPort.load("share-slug-uuid")).thenReturn("family-uuid")
    whenever(loadMenusByFamilyPort.loadAll("family-uuid"))
      .thenReturn(listOf(Menu("menu-uuid", "family-uuid", "김치찌개", "얼큰한 김치찌개", null)))

    // When
    val result = service.operate(cmd)

    // Then
    assertThat(result.items).hasSize(1)
    assertThat(result.items[0].name).isEqualTo("김치찌개")
  }
}
