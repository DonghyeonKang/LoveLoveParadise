package com.deepdeepgo.loveloveparadise.useCases.deleteMenu.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.useCases.deleteMenu.application.port.out.DeleteMenuPort
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.MenuRepository
import java.time.LocalDateTime
import org.springframework.stereotype.Component

@Component
class DeleteMenuPersistAdapter(
  private val menuRepository: MenuRepository,
) : DeleteMenuPort {

  override fun delete(menuId: String) {
    val menu =
      menuRepository.findById(menuId).orElseThrow { NotFoundException("존재하지 않는 메뉴입니다.") }
    menu.deletedAt = LocalDateTime.now()
    menuRepository.save(menu)
  }
}
