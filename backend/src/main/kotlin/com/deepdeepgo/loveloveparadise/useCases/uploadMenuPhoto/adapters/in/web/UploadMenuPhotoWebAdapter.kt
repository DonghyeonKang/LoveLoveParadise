package com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.adapters.`in`.web

import com.deepdeepgo.loveloveparadise.config.exception.UnauthorizedException
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.`in`.UploadMenuPhotoCmd
import com.deepdeepgo.loveloveparadise.useCases.uploadMenuPhoto.application.port.`in`.UploadMenuPhotoUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Menu", description = "메뉴 관련 API")
@RestController
@RequestMapping("/api/v1/menus/photos")
class UploadMenuPhotoWebAdapter(
  private val uploadMenuPhotoUseCase: UploadMenuPhotoUseCase,
) {

  @Operation(
    summary = "메뉴 사진 업로드",
    description = "사진을 업로드하면 원본/중간/썸네일 3종으로 리사이즈하여 저장하고 photoId를 반환한다.",
  )
  @ApiResponse(responseCode = "201", description = "업로드 성공")
  @ApiResponse(responseCode = "400", description = "허용되지 않는 파일 형식")
  @ApiResponse(responseCode = "401", description = "인증이 필요함")
  @PostMapping(consumes = ["multipart/form-data"])
  fun upload(
    @Parameter(description = "인증 쿠키")
    @CookieValue(name = "access_token", required = false)
    accessToken: String?,
    @Parameter(description = "업로드할 이미지 파일") @RequestParam("file") file: MultipartFile,
  ): ResponseEntity<UploadMenuPhotoResponse> {
    if (accessToken.isNullOrBlank()) {
      throw UnauthorizedException("인증이 필요합니다.")
    }

    val result =
      uploadMenuPhotoUseCase.operate(UploadMenuPhotoCmd(accessToken, file.contentType, file.bytes))
    return ResponseEntity.status(HttpStatus.CREATED).body(UploadMenuPhotoResponse(result.photoId))
  }
}
