package com.anas.mealster.service

import com.anas.mealster.domain.model.UserId
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import java.util.Date
import kotlin.io.encoding.Base64

class JwtService(
    @param: Value("\${jwt.secret}") private val secreteBase64: String,
    @param: Value("\${jwt.expiry.minutes}") private val expiryMinutes: Int
) {

    private val secreteKey = Keys.hmacShaKeyFor(Base64.decode(secreteBase64))

    private val accessTokenValidityMs = expiryMinutes * 60 * 1000L
    private val refreshTokenValidityMs = 30 * 24 * 60 * 60 * 1000L

    fun generateAccessToken(userId: UserId) : String{
        return generateToken(userId,"access",accessTokenValidityMs)
    }
    fun generateRefreshToken(userId: UserId) : String{
        return generateToken(userId,"refresh",refreshTokenValidityMs)
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
            .signWith(secreteKey, Jwts.SIG.HS256)
            .compact()
    }

    fun parseAllClaims(token: String) : Claims? {
        return try {
            Jwts.parser()
                .verifyWith(secreteKey)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (_ : Exception){
            null
        }
    }



}