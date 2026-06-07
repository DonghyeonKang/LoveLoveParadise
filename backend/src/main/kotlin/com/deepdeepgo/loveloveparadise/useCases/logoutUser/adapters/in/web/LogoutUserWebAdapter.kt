package com.deepdeepgo.loveloveparadise.useCases.logoutUser.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.useCases.logoutUser.application.port.`in`.LogoutUserCmd
import com.deepdeepgo.loveloveparadise.useCases.logoutUser.application.port.`in`.LogoutUserUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import java.time.Duration
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/v1/auth")
class LogoutUserWebAdapter(
  private val logoutUserUseCase: LogoutUserUseCase,
) {

  @Operation(
    summary = "로그아웃",
    description = "access_token HttpOnly 쿠키를 만료시켜 로그인 세션을 종료한다.",
  )
  @ApiResponse(responseCode = "200", description = "로그아웃 성공 - access_token 쿠키 삭제")
  @PostMapping("/logout")
  fun logout(response: HttpServletResponse): ResponseEntity<Void> {
    logoutUserUseCase.operate(LogoutUserCmd())

    val cookie =
      ResponseCookie.from("access_token", "")
        .httpOnly(true)
        .path("/")
        .maxAge(Duration.ZERO)
        .sameSite("Strict")
        .build()
    response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString())

    return ResponseEntity.ok().build()
  }
}
