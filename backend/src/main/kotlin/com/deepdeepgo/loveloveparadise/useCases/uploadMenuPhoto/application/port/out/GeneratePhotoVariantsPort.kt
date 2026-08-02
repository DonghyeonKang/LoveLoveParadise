package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out

data class PhotoVariants(
  val original: ByteArray,
  val medium: ByteArray,
  val thumb: ByteArray,
)

interface GeneratePhotoVariantsPort {
  fun generate(content: ByteArray): PhotoVariants
}
