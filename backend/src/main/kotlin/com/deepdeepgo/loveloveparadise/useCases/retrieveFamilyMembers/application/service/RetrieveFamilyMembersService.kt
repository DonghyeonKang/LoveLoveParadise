package com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.service

import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.`in`.FamilyMemberSummary
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.`in`.FamilyMembersRetrieved
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.`in`.RetrieveFamilyMembersCmd
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.`in`.RetrieveFamilyMembersUseCase
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.out.LoadFamilyMembersPort
import org.springframework.stereotype.Service

@Service
class RetrieveFamilyMembersService(
  private val validateTokenPort: ValidateTokenPort,
  private val loadUserFamilyPort: LoadUserFamilyPort,
  private val loadFamilyMembersPort: LoadFamilyMembersPort,
) : RetrieveFamilyMembersUseCase {

  override fun operate(cmd: RetrieveFamilyMembersCmd): FamilyMembersRetrieved {
    val userId = validateTokenPort.validate(cmd.accessToken)
    val familyId = loadUserFamilyPort.load(userId)
    val members = loadFamilyMembersPort.loadAll(familyId).map { FamilyMemberSummary(it.name, it.email) }
    return FamilyMembersRetrieved(familyId, members)
  }
}
