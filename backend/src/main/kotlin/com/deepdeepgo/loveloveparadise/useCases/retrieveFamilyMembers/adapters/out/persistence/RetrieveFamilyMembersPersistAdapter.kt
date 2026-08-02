package com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.out.FamilyMember
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.out.LoadFamilyMembersPort
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class RetrieveFamilyMembersPersistAdapter(
  private val userRepository: UserRepository,
) : LoadFamilyMembersPort {

  override fun loadAll(familyId: String): List<FamilyMember> {
    return userRepository.findAllByFamilyId(familyId).map { FamilyMember(it.name, it.email) }
  }
}
