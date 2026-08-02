package com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.`in`

data class FamilyMembersRetrieved(
  val familyId: String,
  val members: List<FamilyMemberSummary>,
)
