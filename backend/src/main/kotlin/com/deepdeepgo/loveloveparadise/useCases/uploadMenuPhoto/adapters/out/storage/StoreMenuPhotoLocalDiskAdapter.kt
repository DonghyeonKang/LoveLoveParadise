package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.adapters.out.storage

import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.PhotoVariants
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.out.StoreMenuPhotoPort
import java.io.IOException
import java.io.UncheckedIOException
import java.nio.file.Files
import java.nio.file.Paths
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class StoreMenuPhotoLocalDiskAdapter(
  @Value("\${app.storage.upload-dir}") private val uploadDir: String,
) : StoreMenuPhotoPort {

  override fun store(photoId: String, variants: PhotoVariants) {
    try {
      val dir = Paths.get(uploadDir, photoId)
      Files.createDirectories(dir)
      Files.write(dir.resolve("original.jpg"), variants.original)
      Files.write(dir.resolve("medium.jpg"), variants.medium)
      Files.write(dir.resolve("thumb.jpg"), variants.thumb)
    } catch (e: IOException) {
      throw UncheckedIOException("사진 저장에 실패했습니다.", e)
    }
  }
}
