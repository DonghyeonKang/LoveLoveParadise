package com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.out

interface LoadFamilyMembersPort {
  fun loadAll(familyId: String): List<FamilyMember>
}
