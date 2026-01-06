package com.anas.mealster.service.auth_service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class JWTService(
    @param:Value("\${jwt.secret}") private val secretBase64: String,
    @param:Value("\${jwt.expiration-minutes}") private val expirationMinutes: Int
) {

    //val secretKey = Keys

    private val accessTokenValidityMs = expirationMinutes * 60 * 1000L
    val refreshTokenValidityMs = 30 * 24 * 60 * 60 * 1000L


}