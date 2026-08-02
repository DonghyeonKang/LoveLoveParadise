package com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.UnauthorizedException
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.`in`.RetrieveMyMenuBoardCmd
import com.deepdeepgo.loveloveparadise.useCases.retrieveMyMenuBoard.application.port.`in`.RetrieveMyMenuBoardUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Menu", description = "메뉴 관련 API")
@RestController
@RequestMapping("/api/v1/menus")
class RetrieveMyMenuBoardWebAdapter(
  private val retrieveMyMenuBoardUseCase: RetrieveMyMenuBoardUseCase,
) {

  @Operation(
    summary = "내 가족 메뉴판 조회",
    description = "로그인한 사용자의 가족 메뉴 목록과 공유 슬러그를 반환한다.",
  )
  @ApiResponse(responseCode = "200", description = "조회 성공")
  @ApiResponse(responseCode = "401", description = "인증이 필요함")
  @GetMapping
  fun retrieve(
    @Parameter(description = "인증 쿠키")
    @CookieValue(name = "access_token", required = false)
    accessToken: String?,
  ): ResponseEntity<RetrieveMyMenuBoardResponse> {
    if (accessToken.isNullOrBlank()) {
      throw UnauthorizedException("인증이 필요합니다.")
    }

    val result = retrieveMyMenuBoardUseCase.operate(RetrieveMyMenuBoardCmd(accessToken))
    val items = result.items.map { MenuItemResponse(it.id, it.name, it.description, it.photoId) }
    return ResponseEntity.ok(RetrieveMyMenuBoardResponse(result.shareSlug, items))
  }
}
