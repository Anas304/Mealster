package com.anas.mealster.service

import com.anas.mealster.domain.exception.InvalidTokenException
import com.anas.mealster.domain.model.UserId
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import kotlin.io.encoding.Base64

@Service
class JwtService(
    @param: Value("\${jwt.secret}") private val secretBase64: String,
    @param: Value("\${jwt.expiry.minutes}") private val expiryMinutes: Int
) {

    private val secretKey = Keys.hmacShaKeyFor(Base64.decode(secretBase64))

    private val accessTokenValidityMs = expiryMinutes * 60 * 1000L
    val refreshTokenValidityMs = 30 * 24 * 60 * 60 * 1000L

    fun validateAccessToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "access"
    }

    fun validateRefreshToken(token: String): Boolean {
        val claims = parseAllClaims(token) ?: return false
        val tokenType = claims["type"] as? String ?: return false
        return tokenType == "refresh"
    }

    fun getUserIdFromToken(token: String) : UserId{
        val claims = parseAllClaims(token) ?: throw InvalidTokenException(
            "This token is invalid"
        )
        return UUID.fromString(claims.subject)
    }

    fun generateAccessToken(userId: UserId): String {
        return generateToken(userId, "access", accessTokenValidityMs)
    }

    fun generateRefreshToken(userId: UserId): String {
        return generateToken(userId, "refresh", refreshTokenValidityMs)
    }

    fun generateToken(
        userId: UserId,
        type: String,
        expiry: Long
    ): String {
        val now = Date()
        val expiryDate = Date(now.time + expiry)

        return Jwts.builder()
            .subject(userId.toString())
            .claim("type", type)
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey, Jwts.SIG.HS256)
            .compact()
    }

    fun parseAllClaims(token: String): Claims? {
        val rawToken = if (token.startsWith("Bearer ")) {
            token.removePrefix("Bearer  ")
        } else token

        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(rawToken)
                .payload
        } catch (_: Exception) {
            null
        }
    }
}