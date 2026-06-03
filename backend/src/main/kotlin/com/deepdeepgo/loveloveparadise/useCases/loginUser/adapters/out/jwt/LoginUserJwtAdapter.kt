package com.deepdeepgo.loveloveparadise.useCases.loginUser.adapters.out.jwt

import com.deepdeepgo.loveloveparadise.useCases.loginUser.application.port.out.IssueTokenPort
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
) : IssueTokenPort {

  override fun issue(userId: String): String {
    val now = Instant.now()
    val key = Keys.hmacShaKeyFor(secret.toByteArray())

    return Jwts.builder()
      .subject(userId)
      .issuedAt(Date.from(now))
      .expiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
      .signWith(key)
      .compact()
  }
}
