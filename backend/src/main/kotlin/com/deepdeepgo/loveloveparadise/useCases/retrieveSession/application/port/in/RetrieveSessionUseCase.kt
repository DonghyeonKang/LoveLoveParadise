package com.deepdeepgo.loveloveparadise.useCases.retrieveSession.application.port.`in`

interface RetrieveSessionUseCase {
  fun operate(cmd: RetrieveSessionCmd): SessionRetrieved
}
