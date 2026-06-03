package com.deepdeepgo.loveloveparadise.support

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc

abstract class WebMvcTestBase {
  @Autowired protected lateinit var mockMvc: MockMvc

  private val objectMapper = ObjectMapper().findAndRegisterModules()

  protected fun json(obj: Any): String = objectMapper.writeValueAsString(obj)
}
