package com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.support.DataJpaTestBase
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.UserEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.UserRepository
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import

@Import(RetrieveFamilyMembersPersistAdapter::class)
class RetrieveFamilyMembersPersistAdapterTest : DataJpaTestBase() {

  @Autowired private lateinit var adapter: RetrieveFamilyMembersPersistAdapter
  @Autowired private lateinit var userRepository: UserRepository

  @Test
  @DisplayName("Scenario: 성공 - familyId에 속한 사용자만 이름/이메일로 조회한다")
  fun load_all_family_members_success() {
    // Given
    val familyId = UUID.randomUUID().toString()
    val otherFamilyId = UUID.randomUUID().toString()
    userRepository.save(
      UserEntity(UUID.randomUUID().toString(), "hong@test.com", "hashed", "홍길동", familyId)
    )
    userRepository.save(
      UserEntity(UUID.randomUUID().toString(), "kim@test.com", "hashed", "김철수", familyId)
    )
    userRepository.save(
      UserEntity(UUID.randomUUID().toString(), "other@test.com", "hashed", "이영희", otherFamilyId)
    )

    // When
    val members = adapter.loadAll(familyId)

    // Then
    assertThat(members).hasSize(2)
    assertThat(members).extracting("name").containsExactlyInAnyOrder("홍길동", "김철수")
  }
}
