package com.deepdeepgo.loveloveparadise

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@Disabled("실행 환경에 PostgreSQL이 필요. 통합 테스트 환경에서만 활성화")
@SpringBootTest
class LoveloveparadiseApplicationTests {

	@Test
	fun contextLoads() {
	}

}
