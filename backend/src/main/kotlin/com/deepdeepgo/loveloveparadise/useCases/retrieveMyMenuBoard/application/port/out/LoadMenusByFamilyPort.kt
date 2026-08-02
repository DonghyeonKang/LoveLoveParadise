package com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.out

import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.out.Menu

interface LoadMenusByFamilyPort {
  fun loadAll(familyId: String): List<Menu>
}
