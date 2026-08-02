package com.deepdeepgo.loveloveparadise.useCases.deleteMenu.application.service

import com.deepdeepgo.loveloveparadise.config.exception.ForbiddenException
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.deleteMenu.application.port.`in`.DeleteMenuCmd
import com.deepdeepgo.loveloveparadise.useCases.deleteMenu.application.port.`in`.DeleteMenuUseCase
import com.deepdeepgo.loveloveparadise.useCases.deleteMenu.application.port.out.DeleteMenuPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.LoadMenuPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteMenuService(
  private val validateTokenPort: ValidateTokenPort,
  private val loadUserFamilyPort: LoadUserFamilyPort,
  private val loadMenuPort: LoadMenuPort,
  private val deleteMenuPort: DeleteMenuPort,
) : DeleteMenuUseCase {

  override fun operate(cmd: DeleteMenuCmd) {
    val userId = validateTokenPort.validate(cmd.accessToken)
    val actingFamilyId = loadUserFamilyPort.load(userId)

    val menu = loadMenuPort.load(cmd.menuId)
    if (menu.familyId != actingFamilyId) {
      throw ForbiddenException("다른 가족의 메뉴는 삭제할 수 없습니다.")
    }

    deleteMenuPort.delete(cmd.menuId)
  }
}
