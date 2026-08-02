package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.adapters.out.storage

import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.GeneratePhotoVariantsPort
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.PhotoVariants
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import net.coobird.thumbnailator.Thumbnails
import org.springframework.stereotype.Component

private const val MEDIUM_MAX_DIMENSION = 1000
private const val THUMB_MAX_DIMENSION = 400

@Component
class GenerateMenuPhotoVariantsThumbnailatorAdapter : GeneratePhotoVariantsPort {

  override fun generate(content: ByteArray): PhotoVariants =
    PhotoVariants(
      original = resize(content, null),
      medium = resize(content, MEDIUM_MAX_DIMENSION),
      thumb = resize(content, THUMB_MAX_DIMENSION),
    )

  private fun resize(content: ByteArray, maxDimension: Int?): ByteArray {
    val output = ByteArrayOutputStream()
    val builder = Thumbnails.of(ByteArrayInputStream(content)).outputFormat("jpg")
    if (maxDimension != null) {
      builder.size(maxDimension, maxDimension)
    } else {
      builder.scale(1.0)
    }
    builder.toOutputStream(output)
    return output.toByteArray()
  }
}
