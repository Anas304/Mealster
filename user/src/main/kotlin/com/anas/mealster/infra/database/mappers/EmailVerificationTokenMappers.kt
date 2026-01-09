package com.anas.mealster.infra.database.mappers

import com.anas.mealster.domain.model.EmailVerificationToken
import com.anas.mealster.infra.database.entities.EmailVerificationTokenEntity

fun EmailVerificationTokenEntity.toEmailVerificationToken() : EmailVerificationToken{
    return EmailVerificationToken(
        id = id,
        token = token,
        user = user.toUser()
    )
}