package com.anas.mealster.api.mappers

import com.anas.mealster.api.dto.AuthenticatedUserDto
import com.anas.mealster.api.dto.UserDto
import com.anas.mealster.domain.model.AuthenticatedUser
import com.anas.mealster.domain.model.User

fun AuthenticatedUser.toAuthenticatedUserDto() : AuthenticatedUserDto{
    return AuthenticatedUserDto(
        user = user.toUserDto(),
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}

fun User.toUserDto() : UserDto{
    return UserDto(
        userId = id,
        username = username,
        email = email,
        hasEmailVerified = hasEmailVerified
    )
}