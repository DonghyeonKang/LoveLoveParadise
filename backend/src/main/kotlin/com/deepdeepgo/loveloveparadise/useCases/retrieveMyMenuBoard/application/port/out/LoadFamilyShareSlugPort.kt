package com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.out

interface LoadFamilyShareSlugPort {
  fun load(familyId: String): String
}
