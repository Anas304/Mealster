package com.anas.mealster.api.dto

import com.anas.mealster.domain.model.UserId

data class UserDto(
    val userId : UserId,
    val username: String,
    val email: String,
    val hasEmailVerified: Boolean
)
