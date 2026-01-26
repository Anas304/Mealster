package com.anas.mealster.service

import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import kotlin.io.encoding.Base64

class JwtService(
    @param: Value("\${jwt.secret}") private val secreteBase64 : String,
    @param: Value("\${jwt.expiry.minutes}") private val expiryMinutes : Int
) {

    private val secreteKey = Keys.hmacShaKeyFor(Base64.decode(secreteBase64))

    private val accessTokenValidityMs = expiryMinutes * 60 * 1000L

}