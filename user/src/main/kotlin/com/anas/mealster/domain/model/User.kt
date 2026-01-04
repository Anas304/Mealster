package com.anas.mealster.domain.model

import java.util.UUID

typealias UserId = UUID

data class User(
    val userId : UserId,
    val username: String,
    val email: String,
    val password: String
)
