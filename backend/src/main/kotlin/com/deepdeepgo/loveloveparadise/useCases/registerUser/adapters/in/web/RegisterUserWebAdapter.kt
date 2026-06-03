package com.deepdeepgo.loveloveparadise.useCases.registerUser.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.`in`.RegisterUserCmd
import com.deepdeepgo.loveloveparadise.useCases.registerUser.application.port.`in`.RegisterUserUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/api/v1/auth")
class RegisterUserWebAdapter(
  private val registerUserUseCase: RegisterUserUseCase,
) {

  @Operation(
    summary = "회원가입",
    description =
      "이메일·비밀번호·이름으로 회원 가입한다. " +
        "familyId가 null이면 새 가족을 생성하고, 값이 있으면 해당 가족에 합류한다.",
  )
  @ApiResponse(responseCode = "201", description = "회원가입 성공")
  @ApiResponse(responseCode = "409", description = "이메일 중복")
  @ApiResponse(responseCode = "404", description = "존재하지 않는 가족 ID")
  @PostMapping("/register")
  fun register(
    @Parameter(description = "회원가입 요청 정보") @RequestBody request: RegisterUserRequest,
  ): ResponseEntity<RegisterUserResponse> {
    val result =
      registerUserUseCase.operate(
        RegisterUserCmd(request.email, request.password, request.name, request.familyId)
      )
    return ResponseEntity.status(HttpStatus.CREATED)
      .body(RegisterUserResponse(result.userId, result.familyId))
  }
}
