package com.deepdeepgo.loveloveparadise.useCases.loginUser.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.`in`.LoginUserCmd
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.`in`.LoginUserUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import java.time.Duration
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/v1/auth")
class LoginUserWebAdapter(
  private val loginUserUseCase: LoginUserUseCase,
) {

  @Operation(
    summary = "로그인",
    description = "이메일·비밀번호 인증 후 JWT 액세스 토큰을 HttpOnly 쿠키로 발급한다.",
  )
  @ApiResponse(responseCode = "200", description = "로그인 성공 - access_token 쿠키 발급")
  @ApiResponse(responseCode = "401", description = "이메일 또는 비밀번호 불일치")
  @PostMapping("/login")
  fun login(
    @RequestBody request: LoginUserRequest,
    response: HttpServletResponse,
  ): ResponseEntity<Void> {
    val result = loginUserUseCase.operate(LoginUserCmd(request.email, request.password))

    val cookie =
      ResponseCookie.from("access_token", result.token)
        .httpOnly(true)
        .path("/")
        .maxAge(Duration.ofMinutes(30))
        .sameSite("Strict")
        .build()
    response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString())

    return ResponseEntity.ok().build()
  }
}
