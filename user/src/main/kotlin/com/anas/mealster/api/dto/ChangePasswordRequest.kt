package com.anas.mealster.api.dto

import com.anas.mealster.api.util.Password
import com.anas.mealster.domain.model.UserId
import jakarta.validation.constraints.NotBlank

data class ChangePasswordRequest(
    val userId: UserId,
    @field:NotBlank
    val oldPassword: String,
    @field:Password
    val newPassword: String
)
