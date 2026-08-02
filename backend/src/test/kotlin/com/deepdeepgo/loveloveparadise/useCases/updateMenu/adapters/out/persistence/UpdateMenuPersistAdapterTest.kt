package com.deepdeepgo.loveloveparadise.useCases.updateMenu.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.support.DataJpaTestBase
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.MenuEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.MenuRepository
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(UpdateMenuPersistAdapter::class)
class UpdateMenuPersistAdapterTest : DataJpaTestBase() {

  @Autowired private lateinit var adapter: UpdateMenuPersistAdapter
  @Autowired private lateinit var menuRepository: MenuRepository

  @Test
  @DisplayName("Scenario: 성공 - 메뉴 ID로 조회하면 소속 familyId를 포함한 정보를 반환한다")
  fun load_menu_success() {
    // Given
    val saved =
      menuRepository.save(
        MenuEntity(UUID.randomUUID().toString(), "family-uuid", "김치찌개", "얼큰한 김치찌개", null)
      )

    // When
    val menu = adapter.load(saved.id)

    // Then
    assertThat(menu.id).isEqualTo(saved.id)
    assertThat(menu.familyId).isEqualTo("family-uuid")
    assertThat(menu.name).isEqualTo("김치찌개")
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 메뉴 조회 시 NotFoundException을 던진다")
  fun load_menu_not_found() {
    assertThatThrownBy { adapter.load("non-existent-id") }
      .isInstanceOf(NotFoundException::class.java)
  }

  @Test
  @DisplayName("Scenario: 성공 - 메뉴 정보를 수정한다")
  fun update_menu_success() {
    // Given
    val saved =
      menuRepository.save(
        MenuEntity(UUID.randomUUID().toString(), "family-uuid", "김치찌개", "얼큰한 김치찌개", null)
      )

    // When
    adapter.update(saved.id, "된장찌개", "구수한 된장찌개", "photo-uuid")

    // Then
    val updated = menuRepository.findById(saved.id).orElseThrow()
    assertThat(updated.name).isEqualTo("된장찌개")
    assertThat(updated.description).isEqualTo("구수한 된장찌개")
    assertThat(updated.photoId).isEqualTo("photo-uuid")
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 메뉴를 수정하려 하면 NotFoundException을 던진다")
  fun update_menu_not_found() {
    assertThatThrownBy { adapter.update("non-existent-id", "이름", "설명", null) }
      .isInstanceOf(NotFoundException::class.java)
  }
}
