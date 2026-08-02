package com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.out.LoadFamilyShareSlugPort
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.out.LoadMenusByFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.FamilyRepository
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.MenuRepository
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.Menu
import org.springframework.stereotype.Component

@Component
class RetrieveMyMenuBoardPersistAdapter(
  private val familyRepository: FamilyRepository,
  private val menuRepository: MenuRepository,
) : LoadFamilyShareSlugPort, LoadMenusByFamilyPort {

  override fun load(familyId: String): String {
    val family =
      familyRepository.findById(familyId).orElseThrow { NotFoundException("존재하지 않는 가족입니다.") }
    return family.shareSlug
  }

  override fun loadAll(familyId: String): List<Menu> {
    return menuRepository.findAllByFamilyId(familyId).map { menu ->
      Menu(menu.id, menu.familyId, menu.name, menu.description, menu.photoId)
    }
  }
}
