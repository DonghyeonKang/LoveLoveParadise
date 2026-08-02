package com.deepdeepgo.loveloveparadise.useCases.createMenu.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.NewMenu
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.SaveMenuPort
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.entity.MenuEntity
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.MenuRepository
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.UserRepository
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class CreateMenuPersistAdapter(
  private val userRepository: UserRepository,
  private val menuRepository: MenuRepository,
) : LoadUserFamilyPort, SaveMenuPort {

  override fun load(userId: String): String {
    val user =
      userRepository.findById(userId).orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
    return user.familyId
  }

  override fun save(newMenu: NewMenu): String {
    val menu =
      MenuEntity(
        id = UUID.randomUUID().toString(),
        familyId = newMenu.familyId,
        name = newMenu.name,
        description = newMenu.description,
        photoId = newMenu.photoId,
      )
    return menuRepository.save(menu).id
  }
}
