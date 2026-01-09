package com.anas.mealster.infra.security

import java.security.SecureRandom
import java.util.Base64

object TokenGenerator {

    fun generateSecureToken() : String{
        val bytes = ByteArray(32) { 0 }

        val secureRandom = SecureRandom()
        secureRandom.nextBytes(bytes)
        return Base64.getUrlEncoder()
            .withoutPadding()
            .encodeToString(bytes)
    }
// padding ensures that this encoded string will be a multiple of 4, so it adds = signs at the end of the encoded string.
}