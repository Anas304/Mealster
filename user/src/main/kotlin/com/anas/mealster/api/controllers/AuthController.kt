package com.anas.mealster.api.controllers

import com.anas.mealster.api.dto.AuthenticatedUserDto
import com.anas.mealster.api.dto.LoginRequest
import com.anas.mealster.api.dto.RegisterRequest
import com.anas.mealster.api.dto.UserDto
import com.anas.mealster.api.mappers.toAuthenticatedUserDto
import com.anas.mealster.api.mappers.toUserDto
import com.anas.mealster.service.auth_service.AuthService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
class AuthController(
    private val authService: AuthService,
) {

    @RequestMapping("/register")
    fun register(
        @Valid @RequestBody body: RegisterRequest
    ): UserDto {
        return authService.register(
            email = body.email,
            username = body.username,
            password = body.password,
        ).toUserDto()
    }

    @RequestMapping("/login")
    fun login(
        @RequestBody body : LoginRequest
    ) : AuthenticatedUserDto{
        return authService.login(
            email = body.email,
            password = body.password
        ).toAuthenticatedUserDto()
    }
}