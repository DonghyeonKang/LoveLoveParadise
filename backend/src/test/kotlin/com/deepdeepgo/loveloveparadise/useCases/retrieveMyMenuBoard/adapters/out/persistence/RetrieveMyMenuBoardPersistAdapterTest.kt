package com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.support.DataJpaTestBase
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.FamilyEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.MenuEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.FamilyRepository
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.MenuRepository
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(RetrieveMyMenuBoardPersistAdapter::class)
class RetrieveMyMenuBoardPersistAdapterTest : DataJpaTestBase() {

  @Autowired private lateinit var adapter: RetrieveMyMenuBoardPersistAdapter
  @Autowired private lateinit var familyRepository: FamilyRepository
  @Autowired private lateinit var menuRepository: MenuRepository

  @Test
  @DisplayName("Scenario: 성공 - familyId로 shareSlug를 조회한다")
  fun load_share_slug_success() {
    // Given
    val family = familyRepository.save(FamilyEntity(UUID.randomUUID().toString(), "my-share-slug"))

    // When
    val shareSlug = adapter.load(family.id)

    // Then
    assertThat(shareSlug).isEqualTo("my-share-slug")
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 familyId로 조회 시 NotFoundException을 던진다")
  fun load_share_slug_not_found() {
    assertThatThrownBy { adapter.load("non-existent-id") }
      .isInstanceOf(NotFoundException::class.java)
  }

  @Test
  @DisplayName("Scenario: 성공 - familyId에 속한 메뉴 목록을 전부 조회한다")
  fun load_all_menus_by_family_success() {
    // Given
    val familyId = UUID.randomUUID().toString()
    val otherFamilyId = UUID.randomUUID().toString()
    menuRepository.save(
      MenuEntity(UUID.randomUUID().toString(), familyId, "김치찌개", "얼큰한 김치찌개", null)
    )
    menuRepository.save(
      MenuEntity(UUID.randomUUID().toString(), familyId, "된장찌개", "구수한 된장찌개", null)
    )
    menuRepository.save(
      MenuEntity(UUID.randomUUID().toString(), otherFamilyId, "마라탕", "매운 마라탕", null)
    )

    // When
    val menus = adapter.loadAll(familyId)

    // Then
    assertThat(menus).hasSize(2)
    assertThat(menus).extracting("name").containsExactlyInAnyOrder("김치찌개", "된장찌개")
  }
}
