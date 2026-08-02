package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out

interface SaveMenuPhotoPort {
  fun save(familyId: String): String
}
