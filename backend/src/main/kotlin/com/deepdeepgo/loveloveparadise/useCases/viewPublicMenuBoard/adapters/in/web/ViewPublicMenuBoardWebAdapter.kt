package com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.`in`.ViewPublicMenuBoardCmd
import com.deepdeepgo.loveloveparadise.useCases.viewPublicMenuBoard.application.port.`in`.ViewPublicMenuBoardUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Menu", description = "메뉴 관련 API")
@RestController
@RequestMapping("/api/v1/menu-boards")
class ViewPublicMenuBoardWebAdapter(
  private val viewPublicMenuBoardUseCase: ViewPublicMenuBoardUseCase,
) {

  @Operation(summary = "공개 메뉴판 조회", description = "공유 슬러그로 가족 메뉴판을 인증 없이 조회한다.")
  @ApiResponse(responseCode = "200", description = "조회 성공")
  @ApiResponse(responseCode = "404", description = "존재하지 않는 공유 슬러그")
  @GetMapping("/{shareSlug}")
  fun view(
    @Parameter(description = "공유 슬러그") @PathVariable shareSlug: String,
  ): ResponseEntity<PublicMenuBoardResponse> {
    val result = viewPublicMenuBoardUseCase.operate(ViewPublicMenuBoardCmd(shareSlug))
    val items =
      result.items.map { PublicMenuItemResponse(it.id, it.name, it.description, it.photoId) }
    return ResponseEntity.ok(PublicMenuBoardResponse(items))
  }
}
