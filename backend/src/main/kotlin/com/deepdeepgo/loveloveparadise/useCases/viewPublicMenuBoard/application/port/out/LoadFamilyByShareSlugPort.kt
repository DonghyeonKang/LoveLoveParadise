package com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.out

interface LoadFamilyByShareSlugPort {
  fun load(shareSlug: String): String
}
