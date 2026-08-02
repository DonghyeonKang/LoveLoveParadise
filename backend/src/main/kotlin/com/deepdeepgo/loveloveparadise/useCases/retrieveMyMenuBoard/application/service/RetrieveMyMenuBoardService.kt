package com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.service

import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.`in`.MenuSummary
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.`in`.MyMenuBoardRetrieved
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.`in`.RetrieveMyMenuBoardCmd
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.`in`.RetrieveMyMenuBoardUseCase
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.out.LoadFamilyShareSlugPort
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.out.LoadMenusByFamilyPort
import org.springframework.stereotype.Service

@Service
class RetrieveMyMenuBoardService(
  private val validateTokenPort: ValidateTokenPort,
  private val loadUserFamilyPort: LoadUserFamilyPort,
  private val loadFamilyShareSlugPort: LoadFamilyShareSlugPort,
  private val loadMenusByFamilyPort: LoadMenusByFamilyPort,
) : RetrieveMyMenuBoardUseCase {

  override fun operate(cmd: RetrieveMyMenuBoardCmd): MyMenuBoardRetrieved {
    val userId = validateTokenPort.validate(cmd.accessToken)
    val familyId = loadUserFamilyPort.load(userId)

    val shareSlug = loadFamilyShareSlugPort.load(familyId)
    val items =
      loadMenusByFamilyPort.loadAll(familyId).map { menu ->
        MenuSummary(menu.id, menu.name, menu.description, menu.photoId)
      }

    return MyMenuBoardRetrieved(shareSlug, items)
  }
}
