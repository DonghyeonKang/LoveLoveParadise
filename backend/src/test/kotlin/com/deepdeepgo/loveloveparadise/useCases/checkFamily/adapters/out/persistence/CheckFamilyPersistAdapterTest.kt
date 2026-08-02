package com.deepdeepgo.loveloveparadise.useCases.checkFamily.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.support.DataJpaTestBase
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.FamilyEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.FamilyRepository
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(CheckFamilyPersistAdapter::class)
class CheckFamilyPersistAdapterTest : DataJpaTestBase() {

  @Autowired private lateinit var adapter: CheckFamilyPersistAdapter
  @Autowired private lateinit var familyRepository: FamilyRepository

  @Test
  @DisplayName("Scenario: 성공 - 존재하는 familyId로 verify 시 예외 없이 완료된다")
  fun verify_success() {
    // Given
    familyRepository.save(FamilyEntity("existing-family-id", "existing-family-slug"))

    // When & Then
    assertThatCode { adapter.verify("existing-family-id") }.doesNotThrowAnyException()
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 familyId로 verify 시 NotFoundException을 던진다")
  fun verify_not_found() {
    assertThatThrownBy { adapter.verify("non-existent-id") }
      .isInstanceOf(NotFoundException::class.java)
      .hasMessage("존재하지 않는 가족입니다.")
  }
}
