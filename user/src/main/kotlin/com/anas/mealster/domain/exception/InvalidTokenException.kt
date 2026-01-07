package com.anas.mealster.domain.exception

class InvalidTokenException(
    override val message: String?
) : RuntimeException(
    message ?: "Invalid token."
)