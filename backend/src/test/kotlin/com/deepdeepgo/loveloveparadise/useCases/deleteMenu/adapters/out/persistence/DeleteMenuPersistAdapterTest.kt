package com.deepdeepgo.loveloveparadise.useCases.deleteMenu.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.support.DataJpaTestBase
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.MenuEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.MenuRepository
import jakarta.persistence.EntityManager
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(DeleteMenuPersistAdapter::class)
class DeleteMenuPersistAdapterTest : DataJpaTestBase() {

  @Autowired private lateinit var adapter: DeleteMenuPersistAdapter
  @Autowired private lateinit var menuRepository: MenuRepository
  @Autowired private lateinit var entityManager: EntityManager

  @Test
  @DisplayName("Scenario: 성공 - 메뉴를 삭제하면 deletedAt이 설정되어 이후 조회되지 않는다")
  fun delete_menu_success() {
    // Given
    val saved =
      menuRepository.save(
        MenuEntity(UUID.randomUUID().toString(), "family-uuid", "김치찌개", "얼큰한 김치찌개", null)
      )

    // When
    adapter.delete(saved.id)
    entityManager.flush()
    entityManager.clear()

    // Then
    assertThat(menuRepository.findById(saved.id)).isEmpty()
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 메뉴를 삭제하려 하면 NotFoundException을 던진다")
  fun delete_menu_not_found() {
    assertThatThrownBy { adapter.delete("non-existent-id") }
      .isInstanceOf(NotFoundException::class.java)
  }
}
