package com.deepdeepgo.loveloveparadise.useCases.deleteMenu.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.UnauthorizedException
import com.deepdeepgo.loveloveparadise.useCases.deleteMenu.application.port.`in`.DeleteMenuCmd
import com.deepdeepgo.loveloveparadise.useCases.deleteMenu.application.port.`in`.DeleteMenuUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Menu", description = "메뉴 관련 API")
@RestController
@RequestMapping("/api/v1/menus")
class DeleteMenuWebAdapter(
  private val deleteMenuUseCase: DeleteMenuUseCase,
) {

  @Operation(summary = "메뉴 삭제", description = "본인 가족 소유의 메뉴 항목을 삭제(soft delete)한다.")
  @ApiResponse(responseCode = "200", description = "삭제 성공")
  @ApiResponse(responseCode = "401", description = "인증이 필요함")
  @ApiResponse(responseCode = "403", description = "다른 가족의 메뉴")
  @ApiResponse(responseCode = "404", description = "존재하지 않는 메뉴")
  @DeleteMapping("/{menuId}")
  fun delete(
    @Parameter(description = "인증 쿠키")
    @CookieValue(name = "access_token", required = false)
    accessToken: String?,
    @Parameter(description = "메뉴 ID") @PathVariable menuId: String,
  ): ResponseEntity<Void> {
    if (accessToken.isNullOrBlank()) {
      throw UnauthorizedException("인증이 필요합니다.")
    }

    deleteMenuUseCase.operate(DeleteMenuCmd(accessToken, menuId))
    return ResponseEntity.ok().build()
  }
}
