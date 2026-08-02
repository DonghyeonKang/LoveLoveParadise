package com.deepdeepgo.loveloveparadise.useCases.updateMenu.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.UnauthorizedException
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.`in`.UpdateMenuCmd
import com.deepdeepgo.loveloveparadise.useCases.updateMenu.application.port.`in`.UpdateMenuUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Menu", description = "메뉴 관련 API")
@RestController
@RequestMapping("/api/v1/menus")
class UpdateMenuWebAdapter(
  private val updateMenuUseCase: UpdateMenuUseCase,
) {

  @Operation(summary = "메뉴 수정", description = "본인 가족 소유의 메뉴 항목을 수정한다.")
  @ApiResponse(responseCode = "200", description = "수정 성공")
  @ApiResponse(responseCode = "401", description = "인증이 필요함")
  @ApiResponse(responseCode = "403", description = "다른 가족의 메뉴")
  @ApiResponse(responseCode = "404", description = "존재하지 않는 메뉴")
  @PutMapping("/{menuId}")
  fun update(
    @Parameter(description = "인증 쿠키")
    @CookieValue(name = "access_token", required = false)
    accessToken: String?,
    @Parameter(description = "메뉴 ID") @PathVariable menuId: String,
    @Parameter(description = "메뉴 수정 요청 정보") @RequestBody request: UpdateMenuRequest,
  ): ResponseEntity<UpdateMenuResponse> {
    if (accessToken.isNullOrBlank()) {
      throw UnauthorizedException("인증이 필요합니다.")
    }

    val result =
      updateMenuUseCase.operate(
        UpdateMenuCmd(accessToken, menuId, request.name, request.description, request.photoId)
      )
    return ResponseEntity.ok(UpdateMenuResponse(result.menuId))
  }
}
