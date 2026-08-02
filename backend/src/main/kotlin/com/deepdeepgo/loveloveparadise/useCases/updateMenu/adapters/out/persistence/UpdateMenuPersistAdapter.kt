package com.deepdeepgo.loveloveparadise.useCases.updateMenu.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.MenuRepository
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.LoadMenuPort
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.Menu
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.UpdateMenuPort
import org.springframework.stereotype.Component

@Component
class UpdateMenuPersistAdapter(
  private val menuRepository: MenuRepository,
) : LoadMenuPort, UpdateMenuPort {

  override fun load(menuId: String): Menu {
    val menu =
      menuRepository.findById(menuId).orElseThrow { NotFoundException("존재하지 않는 메뉴입니다.") }
    return Menu(menu.id, menu.familyId, menu.name, menu.description, menu.photoId)
  }

  override fun update(menuId: String, name: String, description: String, photoId: String?) {
    val menu =
      menuRepository.findById(menuId).orElseThrow { NotFoundException("존재하지 않는 메뉴입니다.") }
    menu.name = name
    menu.description = description
    menu.photoId = photoId
    menuRepository.save(menu)
  }
}
