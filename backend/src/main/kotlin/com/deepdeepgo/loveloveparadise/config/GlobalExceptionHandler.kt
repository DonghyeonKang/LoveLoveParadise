package com.deepdeepgo.loveloveparadise.config

import com.deepdeepgo.loveloveparadise.config.exception.ConflictException
import com.deepdeepgo.loveloveparadise.config.exception.ForbiddenException
import com.deepdeepgo.loveloveparadise.config.exception.InvalidRequestException
import com.deepdeepgo.loveloveparadise.config.exception.NotFoundException
import com.deepdeepgo.loveloveparadise.config.exception.UnauthorizedException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

  @ExceptionHandler(UnauthorizedException::class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  fun handleUnauthorized(e: UnauthorizedException) {}

  @ExceptionHandler(ForbiddenException::class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  fun handleForbidden(e: ForbiddenException) {}

  @ExceptionHandler(ConflictException::class)
  @ResponseStatus(HttpStatus.CONFLICT)
  fun handleConflict(e: ConflictException) {}

  @ExceptionHandler(NotFoundException::class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  fun handleNotFound(e: NotFoundException) {}

  @ExceptionHandler(InvalidRequestException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleInvalidRequest(e: InvalidRequestException) {}
}
