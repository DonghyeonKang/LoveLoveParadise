package com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.service

import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.out.LoadMenusByFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.`in`.PublicMenuBoardRetrieved
import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.`in`.PublicMenuItem
import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.`in`.ViewPublicMenuBoardCmd
import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.`in`.ViewPublicMenuBoardUseCase
import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.out.LoadFamilyByShareSlugPort
import org.springframework.stereotype.Service

@Service
class ViewPublicMenuBoardService(
  private val loadFamilyByShareSlugPort: LoadFamilyByShareSlugPort,
  private val loadMenusByFamilyPort: LoadMenusByFamilyPort,
) : ViewPublicMenuBoardUseCase {

  override fun operate(cmd: ViewPublicMenuBoardCmd): PublicMenuBoardRetrieved {
    val familyId = loadFamilyByShareSlugPort.load(cmd.shareSlug)
    val items =
      loadMenusByFamilyPort.loadAll(familyId).map { menu ->
        PublicMenuItem(menu.id, menu.name, menu.description, menu.photoId)
      }
    return PublicMenuBoardRetrieved(items)
  }
}
