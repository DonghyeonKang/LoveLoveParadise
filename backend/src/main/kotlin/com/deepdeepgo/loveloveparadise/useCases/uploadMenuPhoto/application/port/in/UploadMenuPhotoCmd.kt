package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.`in`

data class UploadMenuPhotoCmd(
  val accessToken: String,
  val contentType: String?,
  val content: ByteArray,
)
