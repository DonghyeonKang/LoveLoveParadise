package com.deepdeepgo.loveloveparadise.useCases.checkFamily.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.useCases.checkFamily.application.port.`in`.CheckFamilyCmd
import com.deepdeepgo.loveloveparadise.useCases.checkFamily.application.port.`in`.CheckFamilyUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Family", description = "가족 관련 API")
@RestController
@RequestMapping("/api/v1/families")
class CheckFamilyWebAdapter(
  private val checkFamilyUseCase: CheckFamilyUseCase,
) {

  @Operation(summary = "가족 존재 확인", description = "familyId로 가족 존재 여부를 확인한다. 존재하면 200, 없으면 404를 반환한다.")
  @ApiResponse(responseCode = "200", description = "가족 존재 확인 성공")
  @ApiResponse(responseCode = "404", description = "존재하지 않는 가족 ID")
  @GetMapping("/{familyId}")
  fun checkFamily(
    @Parameter(description = "확인할 가족 ID") @PathVariable familyId: String,
  ): ResponseEntity<Void> {
    checkFamilyUseCase.operate(CheckFamilyCmd(familyId))
    return ResponseEntity.ok().build()
  }
}
