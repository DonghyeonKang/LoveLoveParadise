package com.deepdeepgo.loveloveparadise.useCases.checkFamily.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.useCases.checkFamily.application.port.out.VerifyFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.FamilyRepository
import org.springframework.stereotype.Component

@Component
class CheckFamilyPersistAdapter(
  private val familyRepository: FamilyRepository,
) : VerifyFamilyPort {

  override fun verify(familyId: String) {
    if (!familyRepository.existsById(familyId)) {
      throw NotFoundException("존재하지 않는 가족입니다.")
    }
  }
}
