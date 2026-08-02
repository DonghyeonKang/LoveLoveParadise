package com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.`in`

interface RetrieveFamilyMembersUseCase {
  fun operate(cmd: RetrieveFamilyMembersCmd): FamilyMembersRetrieved
}
