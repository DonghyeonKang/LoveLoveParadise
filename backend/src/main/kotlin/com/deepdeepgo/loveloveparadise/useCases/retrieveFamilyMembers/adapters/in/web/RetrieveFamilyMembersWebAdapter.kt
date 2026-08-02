package com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.UnauthorizedException
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.`in`.RetrieveFamilyMembersCmd
import com.deepdeepgo.loveloveparadise.useCases.retrieveFamilyMembers.application.port.`in`.RetrieveFamilyMembersUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Family", description = "가족 관련 API")
@RestController
@RequestMapping("/api/v1/families")
class RetrieveFamilyMembersWebAdapter(
  private val retrieveFamilyMembersUseCase: RetrieveFamilyMembersUseCase,
) {

  @Operation(
    summary = "내 가족 구성원 조회",
    description = "로그인한 사용자가 속한 가족의 ID와 구성원(이름, 이메일) 목록을 반환한다.",
  )
  @ApiResponse(responseCode = "200", description = "조회 성공")
  @ApiResponse(responseCode = "401", description = "인증이 필요함")
  @GetMapping("/me")
  fun retrieve(
    @Parameter(description = "인증 쿠키")
    @CookieValue(name = "access_token", required = false)
    accessToken: String?,
  ): ResponseEntity<RetrieveFamilyMembersResponse> {
    if (accessToken.isNullOrBlank()) {
      throw UnauthorizedException("인증이 필요합니다.")
    }

    val result = retrieveFamilyMembersUseCase.operate(RetrieveFamilyMembersCmd(accessToken))
    val members = result.members.map { FamilyMemberResponse(it.name, it.email) }
    return ResponseEntity.ok(RetrieveFamilyMembersResponse(result.familyId, members))
  }
}
