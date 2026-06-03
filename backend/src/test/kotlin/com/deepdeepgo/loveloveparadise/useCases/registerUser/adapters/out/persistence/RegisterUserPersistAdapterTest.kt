package com.deepdeepgo.loveloveparadise.useCases.registerUser.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.support.DataJpaTestBase
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.NewUser
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.FamilyEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.FamilyRepository
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(RegisterUserPersistAdapter::class)
class RegisterUserPersistAdapterTest : DataJpaTestBase() {

  @Autowired private lateinit var adapter: RegisterUserPersistAdapter
  @Autowired private lateinit var userRepository: UserRepository
  @Autowired private lateinit var familyRepository: FamilyRepository

  @Test
  @DisplayName("Scenario: 성공 - 새 가족을 생성하고 familyId를 반환한다")
  fun save_family_returns_id() {
    // When
    val familyId = adapter.save()

    // Then
    assertThat(familyId).isNotBlank()
    assertThat(familyRepository.findById(familyId)).isPresent
  }

  @Test
  @DisplayName("Scenario: 성공 - 존재하는 familyId 조회 시 해당 id를 반환한다")
  fun load_family_success() {
    // Given
    val savedId = familyRepository.save(FamilyEntity("test-family-id")).id

    // When
    val result = adapter.load(savedId)

    // Then
    assertThat(result).isEqualTo(savedId)
  }

  @Test
  @DisplayName("Scenario: 실패 - 존재하지 않는 familyId 조회 시 NotFoundException을 던진다")
  fun load_family_not_found() {
    assertThatThrownBy { adapter.load("non-existent-id") }
      .isInstanceOf(NotFoundException::class.java)
  }

  @Test
  @DisplayName("Scenario: 성공 - 사용자를 저장하고 userId를 반환한다")
  fun save_user_returns_id() {
    // Given
    val familyId = familyRepository.save(FamilyEntity("family-for-user")).id
    val newUser = NewUser("user@test.com", "hashed_password", "홍길동", familyId)

    // When
    val userId = adapter.save(newUser)

    // Then
    assertThat(userId).isNotBlank()
    assertThat(userRepository.findById(userId)).isPresent
  }

  @Test
  @DisplayName("Scenario: 성공 - 이메일 미존재 시 existsByEmail이 false를 반환한다")
  fun exists_by_email_returns_false() {
    assertThat(adapter.existsByEmail("notexist@test.com")).isFalse()
  }

  @Test
  @DisplayName("Scenario: 성공 - 이메일 존재 시 existsByEmail이 true를 반환한다")
  fun exists_by_email_returns_true() {
    // Given
    val familyId = familyRepository.save(FamilyEntity("family-email-test")).id
    adapter.save(NewUser("exist@test.com", "hashed", "테스트", familyId))

    // When & Then
    assertThat(adapter.existsByEmail("exist@test.com")).isTrue()
  }
}
