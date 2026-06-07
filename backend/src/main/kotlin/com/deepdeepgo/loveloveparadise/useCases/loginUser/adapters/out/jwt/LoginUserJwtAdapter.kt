package com.deepdeepgo.loveloveparadise.useCases.loginUser.adapters.out.jwt

import com.deepdeepgo.loveloveparadise.config.exception.UnauthorizedException
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.IssueTokenPort
import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.ValidateTokenPort
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class LoginUserJwtAdapter(
  @Value("\${jwt.secret}") private val secret: String,
) : IssueTokenPort, ValidateTokenPort {

  private fun signingKey() = Keys.hmacShaKeyFor(secret.toByteArray())

  override fun issue(userId: String): String {
    val now = Instant.now()

    return Jwts.builder()
      .subject(userId)
      .issuedAt(Date.from(now))
      .expiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
      .signWith(signingKey())
      .compact()
  }

  override fun validate(token: String): String {
    try {
      return Jwts.parser()
        .verifyWith(signingKey())
        .build()
        .parseSignedClaims(token)
        .payload
        .subject
    } catch (_: ExpiredJwtException) {
      throw UnauthorizedException("인증이 만료되었습니다.")
    } catch (_: Exception) {
      throw UnauthorizedException("유효하지 않은 인증 정보입니다.")
    }
  }
}
