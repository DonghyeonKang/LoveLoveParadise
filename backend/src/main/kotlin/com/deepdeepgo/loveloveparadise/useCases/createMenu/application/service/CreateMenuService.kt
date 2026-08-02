package com.deepdeepgo.loveloveparadise.useCases.createMenu.application.service

import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.`in`.CreateMenuCmd
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.`in`.CreateMenuUseCase
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.`in`.MenuCreated
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.NewMenu
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.SaveMenuPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CreateMenuService(
  private val validateTokenPort: ValidateTokenPort,
  private val loadUserFamilyPort: LoadUserFamilyPort,
  private val saveMenuPort: SaveMenuPort,
) : CreateMenuUseCase {

  override fun operate(cmd: CreateMenuCmd): MenuCreated {
    val userId = validateTokenPort.validate(cmd.accessToken)
    val familyId = loadUserFamilyPort.load(userId)
    val menuId = saveMenuPort.save(NewMenu(familyId, cmd.name, cmd.description, cmd.photoId))
    return MenuCreated(menuId)
  }
}
