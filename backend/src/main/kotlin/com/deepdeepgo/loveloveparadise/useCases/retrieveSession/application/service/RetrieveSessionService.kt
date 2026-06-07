package com.deepdeepgo.loveloveparadise.useCases.retrieveSession.application.service

import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import com.deepdeepgo.loveloveparadise.useCases.retrieveSession.application.port.`in`.RetrieveSessionCmd
import com.deepdeepgo.loveloveparadise.useCases.retrieveSession.application.port.`in`.RetrieveSessionUseCase
import com.deepdeepgo.loveloveparadise.useCases.retrieveSession.application.port.`in`.SessionRetrieved
import com.deepdeepgo.loveloveparadise.useCases.retrieveSession.application.port.out.LoadUserProfilePort
import org.springframework.stereotype.Service

@Service
class RetrieveSessionService(
  private val validateTokenPort: ValidateTokenPort,
  private val loadUserProfilePort: LoadUserProfilePort,
) : RetrieveSessionUseCase {

  override fun operate(cmd: RetrieveSessionCmd): SessionRetrieved {
    val userId = validateTokenPort.validate(cmd.token)
    val profile = loadUserProfilePort.load(userId)
    return SessionRetrieved(profile.userId, profile.name, profile.email)
  }
}
