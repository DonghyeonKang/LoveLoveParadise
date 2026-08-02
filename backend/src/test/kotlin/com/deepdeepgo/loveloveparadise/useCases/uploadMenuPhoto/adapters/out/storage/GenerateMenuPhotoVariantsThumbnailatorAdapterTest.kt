package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.adapters.out.storage

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class GenerateMenuPhotoVariantsThumbnailatorAdapterTest {

  private val adapter = GenerateMenuPhotoVariantsThumbnailatorAdapter()

  @Test
  @DisplayName("Scenario: 성공 - 큰 이미지를 업로드하면 medium/thumb이 지정된 최대 크기 이내로 리사이즈된다")
  fun generate_resizes_within_bounds() {
    // Given
    val content = createTestImage(1600, 1200)

    // When
    val variants = adapter.generate(content)

    // Then
    val original = ImageIO.read(ByteArrayInputStream(variants.original))
    val medium = ImageIO.read(ByteArrayInputStream(variants.medium))
    val thumb = ImageIO.read(ByteArrayInputStream(variants.thumb))

    assertThat(original.width).isEqualTo(1600)
    assertThat(medium.width).isLessThanOrEqualTo(1000)
    assertThat(medium.height).isLessThanOrEqualTo(1000)
    assertThat(thumb.width).isLessThanOrEqualTo(400)
    assertThat(thumb.height).isLessThanOrEqualTo(400)
  }

  private fun createTestImage(width: Int, height: Int): ByteArray {
    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
    val output = ByteArrayOutputStream()
    ImageIO.write(image, "jpg", output)
    return output.toByteArray()
  }
}
