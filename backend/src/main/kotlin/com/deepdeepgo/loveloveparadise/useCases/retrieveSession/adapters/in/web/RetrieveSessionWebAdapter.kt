package com.deepdeepgo.loveloveparadise.useCases.retrieveSession.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.UnauthorizedException
import com.deepdeepgo.loveloveparadise.useCases.retrieveSession.application.port.`in`.RetrieveSessionCmd
import com.deepdeepgo.loveloveparadise.useCases.retrieveSession.application.port.`in`.RetrieveSessionUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/v1/auth")
class RetrieveSessionWebAdapter(
  private val retrieveSessionUseCase: RetrieveSessionUseCase,
) {

  @Operation(summary = "세션 조회", description = "access_token 쿠키로 현재 로그인 사용자 정보를 반환한다.")
  @ApiResponse(responseCode = "200", description = "로그인 상태")
  @ApiResponse(responseCode = "401", description = "미로그인 또는 만료된 토큰")
  @GetMapping("/me")
  fun me(
    @CookieValue(name = "access_token", required = false) accessToken: String?,
  ): ResponseEntity<RetrieveSessionResponse> {
    if (accessToken.isNullOrBlank()) {
      throw UnauthorizedException("인증이 필요합니다.")
    }

    val result = retrieveSessionUseCase.operate(RetrieveSessionCmd(accessToken))
    return ResponseEntity.ok(RetrieveSessionResponse(result.name, result.email))
  }
}
