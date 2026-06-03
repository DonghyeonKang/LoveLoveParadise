package com.deepdeepgo.loveloveparadise.useCases.checkFamily.application.service

import com.deepdeepgo.loveloveparadise.useCases.checkFamily.application.port.`in`.CheckFamilyCmd
import com.deepdeepgo.loveloveparadise.useCases.checkFamily.application.port.`in`.CheckFamilyUseCase
import com.deepdeepgo.loveloveparadise.useCases.checkFamily.application.port.out.VerifyFamilyPort
import org.springframework.stereotype.Service

@Service
class CheckFamilyService(
  private val verifyFamilyPort: VerifyFamilyPort,
) : CheckFamilyUseCase {

  override fun operate(cmd: CheckFamilyCmd) {
    verifyFamilyPort.verify(cmd.familyId)
  }
}
