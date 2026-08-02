package com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.service

import com.deepdeepgo.loveloveparadise.config.exception.ForbiddenException
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.`in`.MenuUpdated
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.`in`.UpdateMenuCmd
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.`in`.UpdateMenuUseCase
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.LoadMenuPort
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.UpdateMenuPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdateMenuService(
  private val validateTokenPort: ValidateTokenPort,
  private val loadUserFamilyPort: LoadUserFamilyPort,
  private val loadMenuPort: LoadMenuPort,
  private val updateMenuPort: UpdateMenuPort,
) : UpdateMenuUseCase {

  override fun operate(cmd: UpdateMenuCmd): MenuUpdated {
    val userId = validateTokenPort.validate(cmd.accessToken)
    val actingFamilyId = loadUserFamilyPort.load(userId)

    val menu = loadMenuPort.load(cmd.menuId)
    if (menu.familyId != actingFamilyId) {
      throw ForbiddenException("다른 가족의 메뉴는 수정할 수 없습니다.")
    }

    updateMenuPort.update(cmd.menuId, cmd.name, cmd.description, cmd.photoId)
    return MenuUpdated(cmd.menuId)
  }
}
