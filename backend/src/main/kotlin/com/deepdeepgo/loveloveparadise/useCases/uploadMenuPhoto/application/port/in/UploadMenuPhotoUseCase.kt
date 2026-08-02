package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.`in`

interface UploadMenuPhotoUseCase {
  fun operate(cmd: UploadMenuPhotoCmd): MenuPhotoUploaded
}
