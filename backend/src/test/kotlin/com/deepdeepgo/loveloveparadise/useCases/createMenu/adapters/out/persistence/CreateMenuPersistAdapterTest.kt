package com.deepdeepgo.loveloveparadise.useCases.createMenu.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.support.DataJpaTestBase
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.NewMenu
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.FamilyEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.UserEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.FamilyRepository
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.MenuRepository
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(CreateMenuPersistAdapter::class)
class CreateMenuPersistAdapterTest : DataJpaTestBase() {

  @Autowired private lateinit var adapter: CreateMenuPersistAdapter
  @Autowired private lateinit var userRepository: UserRepository
  @Autowired private lateinit var familyRepository: FamilyRepository
  @Autowired private lateinit var menuRepository: MenuRepository

  @Test
  @DisplayName("Scenario: 성공 - 사용자 ID로 소속 가족 ID를 조회한다")
  fun load_user_family_success() {
    // Given
    val family = familyRepository.save(FamilyEntity("family-1", "family-1-slug"))
    userRepository.save(
      UserEntity(
        id = "user-1",
        email = "user1@test.com",
        password = "hashed",
        name = "홍길동",
        familyId = family.id,
      )
    )

    // When
    val result = adapter.load("user-1")

    // Then
    assertThat(result).isEqualTo(family.id)
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 사용자 ID 조회 시 NotFoundException을 던진다")
  fun load_user_family_not_found() {
    assertThatThrownBy { adapter.load("non-existent-user") }
      .isInstanceOf(NotFoundException::class.java)
  }

  @Test
  @DisplayName("Scenario: 성공 - 메뉴를 저장하고 menuId를 반환한다")
  fun save_menu_returns_id() {
    // Given
    val family = familyRepository.save(FamilyEntity("family-2", "family-2-slug"))
    val newMenu = NewMenu(family.id, "김치찌개", "얼큰한 김치찌개", null)

    // When
    val menuId = adapter.save(newMenu)

    // Then
    assertThat(menuId).isNotBlank()
    val saved = menuRepository.findById(menuId)
    assertThat(saved).isPresent
    assertThat(saved.get().name).isEqualTo("김치찌개")
    assertThat(saved.get().familyId).isEqualTo(family.id)
  }
}
