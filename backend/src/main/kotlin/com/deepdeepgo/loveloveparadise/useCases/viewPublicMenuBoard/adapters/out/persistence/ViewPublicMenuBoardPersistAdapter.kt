package com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.adapters.out.persistence

import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.useCases.shared.adapters.out.persistence.repository.FamilyRepository
import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.out.LoadFamilyByShareSlugPort
import org.springframework.stereotype.Component

@Component
class ViewPublicMenuBoardPersistAdapter(
  private val familyRepository: FamilyRepository,
) : LoadFamilyByShareSlugPort {

  override fun load(shareSlug: String): String {
    val family =
      familyRepository.findByShareSlug(shareSlug).orElseThrow {
        NotFoundException("존재하지 않는 메뉴판입니다.")
      }
    return family.id
  }
}
