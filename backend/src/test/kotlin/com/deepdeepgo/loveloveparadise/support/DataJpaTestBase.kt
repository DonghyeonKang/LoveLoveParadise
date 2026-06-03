package com.deepdeepgo.loveloveparadise.support

import com.deepdeepgo.loveloveparadise.config.JpaConfig
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestPropertySource

@DataJpaTest
@Import(JpaConfig::class)
@TestPropertySource(properties = ["spring.jpa.hibernate.ddl-auto=create-drop"])
abstract class DataJpaTestBase
