package com.anas.mealster.api.controllers

import com.anas.mealster.api.dto.AuthenticatedUserDto
import com.anas.mealster.api.dto.ChangePasswordRequest
import com.anas.mealster.api.dto.EmailRequest
import com.anas.mealster.api.dto.LoginRequest
import com.anas.mealster.api.dto.RefreshRequest
import com.anas.mealster.api.dto.RegisterRequest
import com.anas.mealster.api.dto.ResetPasswordRequest
import com.anas.mealster.api.dto.UserDto
import com.anas.mealster.api.mappers.toAuthenticatedUserDto
import com.anas.mealster.api.mappers.toUserDto
import com.anas.mealster.service.AuthService
import com.anas.mealster.service.EmailVerificationService
import com.anas.mealster.service.PasswordResetService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
class AuthController(
    private val authService: AuthService,
    private val emailVerificationService: EmailVerificationService,
    private val passwordResetService: PasswordResetService
) {

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody body: RegisterRequest
    ): UserDto {
        return authService.register(
            email = body.email,
            username = body.username,
            password = body.password,
        ).toUserDto()
    }

    @PostMapping("/login")
    fun login(
        @RequestBody body: LoginRequest
    ): AuthenticatedUserDto {
        return authService.login(
            email = body.email,
            password = body.password
        ).toAuthenticatedUserDto()
    }

    @PostMapping("/refresh")
    fun refresh(
        @RequestBody body: RefreshRequest
    ): AuthenticatedUserDto {
        return authService.refresh(refreshToken = body.refreshToken).toAuthenticatedUserDto()
    }

    @PostMapping("/logout")
    fun logout(
        @RequestBody body: RefreshRequest
    ) {
        authService.logout(refreshToken = body.refreshToken)
    }

    @GetMapping("/verify")
    fun verify(
        @RequestParam token: String
    ) {
        emailVerificationService.verifyEmail(token)
    }

    fun forgotPassword(
        @Valid @RequestBody body : EmailRequest
    ){
        passwordResetService.resetPasswordRequest(body.email)
    }

    @PostMapping("reset-password")
    fun resetPassword(
        @Valid @RequestBody body: ResetPasswordRequest
    ) {
        passwordResetService.resetPassword(
            token = body.token,
            newPassword = body.newPassword
        )
    }

    @PostMapping("change-password")
    fun changePassword(
        @Valid @RequestBody body: ChangePasswordRequest
    ) {
       //TODO: Extract request user ID and call service
    }
}