package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out

interface StoreMenuPhotoPort {
  fun store(photoId: String, variants: PhotoVariants)
}
