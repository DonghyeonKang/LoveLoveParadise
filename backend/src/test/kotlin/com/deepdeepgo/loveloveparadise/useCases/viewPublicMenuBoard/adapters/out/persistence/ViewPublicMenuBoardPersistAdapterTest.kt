package com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.support.DataJpaTestBase
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.FamilyEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.FamilyRepository
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(ViewPublicMenuBoardPersistAdapter::class)
class ViewPublicMenuBoardPersistAdapterTest : DataJpaTestBase() {

  @Autowired private lateinit var adapter: ViewPublicMenuBoardPersistAdapter
  @Autowired private lateinit var familyRepository: FamilyRepository

  @Test
  @DisplayName("Scenario: 성공 - 공유 슬러그로 familyId를 조회한다")
  fun load_family_by_share_slug_success() {
    // Given
    val family = familyRepository.save(FamilyEntity(UUID.randomUUID().toString(), "my-share-slug"))

    // When
    val familyId = adapter.load("my-share-slug")

    // Then
    assertThat(familyId).isEqualTo(family.id)
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 공유 슬러그로 조회 시 NotFoundException을 던진다")
  fun load_family_by_share_slug_not_found() {
    assertThatThrownBy { adapter.load("unknown-slug") }.isInstanceOf(NotFoundException::class.java)
  }
}
