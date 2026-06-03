package com.deepdeepgo.loveloveparadise.useCases.registerUser.application.service

import com.deepdeepgo.loveloveparadise.config.exception.ConflictException
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.`in`.RegisterUserCmd
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.`in`.RegisterUserUseCase
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.`in`.UserRegistered
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.CheckEmailPort
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.LoadFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.NewUser
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.SaveFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.out.SaveUserPort
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegisterUserService(
  private val checkEmailPort: CheckEmailPort,
  private val loadFamilyPort: LoadFamilyPort,
  private val saveFamilyPort: SaveFamilyPort,
  private val saveUserPort: SaveUserPort,
) : RegisterUserUseCase {

  private val passwordEncoder = BCryptPasswordEncoder()

  override fun operate(cmd: RegisterUserCmd): UserRegistered {
    if (checkEmailPort.existsByEmail(cmd.email)) {
      throw ConflictException("이미 사용 중인 이메일입니다.")
    }

    val familyId = cmd.familyId?.let { loadFamilyPort.load(it) } ?: saveFamilyPort.save()

    val hashedPassword = passwordEncoder.encode(cmd.password)!!
    val userId = saveUserPort.save(NewUser(cmd.email, hashedPassword, cmd.name, familyId))

    return UserRegistered(userId, familyId)
  }
}
