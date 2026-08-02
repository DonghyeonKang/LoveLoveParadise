package com.deepdeepgo.loveloveparadise.useCases.createMenu.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.UnauthorizedException
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.`in`.CreateMenuCmd
import com.deepdeepgo.loveloveparadise.useCases.createMenu.application.port.`in`.CreateMenuUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Menu", description = "메뉴 관련 API")
@RestController
@RequestMapping("/api/v1/menus")
class CreateMenuWebAdapter(
  private val createMenuUseCase: CreateMenuUseCase,
) {

  @Operation(summary = "메뉴 생성", description = "로그인한 사용자의 가족에 새 메뉴 항목을 생성한다.")
  @ApiResponse(responseCode = "201", description = "메뉴 생성 성공")
  @ApiResponse(responseCode = "401", description = "인증이 필요함")
  @PostMapping
  fun create(
    @Parameter(description = "인증 쿠키") @CookieValue(name = "access_token", required = false) accessToken: String?,
    @Parameter(description = "메뉴 생성 요청 정보") @RequestBody request: CreateMenuRequest,
  ): ResponseEntity<CreateMenuResponse> {
    if (accessToken.isNullOrBlank()) {
      throw UnauthorizedException("인증이 필요합니다.")
    }

    val result =
      createMenuUseCase.operate(
        CreateMenuCmd(accessToken, request.name, request.description, request.photoId)
      )
    return ResponseEntity.status(HttpStatus.CREATED).body(CreateMenuResponse(result.menuId))
  }
}
