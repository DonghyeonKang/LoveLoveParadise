package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.service

import com.deepdeepgo.loveloveparadise.config.exception.InvalidRequestException
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.out.LoadUserFamilyPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.`in`.MenuPhotoUploaded
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.`in`.UploadMenuPhotoCmd
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.`in`.UploadMenuPhotoUseCase
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.GeneratePhotoVariantsPort
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.SaveMenuPhotoPort
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.StoreMenuPhotoPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val ALLOWED_CONTENT_TYPES = setOf("image/jpeg", "image/png")

@Service
@Transactional
class UploadMenuPhotoService(
  private val validateTokenPort: ValidateTokenPort,
  private val loadUserFamilyPort: LoadUserFamilyPort,
  private val generatePhotoVariantsPort: GeneratePhotoVariantsPort,
  private val saveMenuPhotoPort: SaveMenuPhotoPort,
  private val storeMenuPhotoPort: StoreMenuPhotoPort,
) : UploadMenuPhotoUseCase {

  override fun operate(cmd: UploadMenuPhotoCmd): MenuPhotoUploaded {
    if (cmd.contentType !in ALLOWED_CONTENT_TYPES) {
      throw InvalidRequestException("JPEG 또는 PNG 이미지만 업로드할 수 있습니다.")
    }

    val userId = validateTokenPort.validate(cmd.accessToken)
    val familyId = loadUserFamilyPort.load(userId)

    val variants = generatePhotoVariantsPort.generate(cmd.content)
    val photoId = saveMenuPhotoPort.save(familyId)
    storeMenuPhotoPort.store(photoId, variants)

    return MenuPhotoUploaded(photoId)
  }
}
