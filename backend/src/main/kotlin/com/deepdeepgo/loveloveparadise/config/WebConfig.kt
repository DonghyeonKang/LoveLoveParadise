package com.deepdeepgo.loveloveparadise.config

import java.nio.file.Paths
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
  @Value("\${app.storage.upload-dir}") private val uploadDir: String,
) : WebMvcConfigurer {

  override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
    registry
      .addResourceHandler("/api/v1/photos/**")
      .addResourceLocations("file:${Paths.get(uploadDir).toAbsolutePath()}/")
  }
}
