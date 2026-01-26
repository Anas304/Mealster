package com.anas.mealster.service

import org.springframework.beans.factory.annotation.Value

class JwtService(
    @param: Value("\${jwt.secret}") private val secretKey : String
) {

}