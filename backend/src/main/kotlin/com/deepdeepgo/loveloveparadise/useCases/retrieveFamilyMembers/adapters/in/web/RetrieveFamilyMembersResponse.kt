package com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.adapters.`in`.web

data class RetrieveFamilyMembersResponse(
  val familyId: String,
  val members: List<FamilyMemberResponse>,
)
