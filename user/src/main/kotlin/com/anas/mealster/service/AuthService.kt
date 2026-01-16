package com.anas.mealster.service

import com.anas.mealster.domain.exception.EmailNotVerifiedException
import com.anas.mealster.domain.exception.InvalidCredentialException
import com.anas.mealster.domain.exception.InvalidTokenException
import com.anas.mealster.domain.exception.UserAlreadyExistException
import com.anas.mealster.domain.exception.UserNotFoundException
import com.anas.mealster.domain.model.AuthenticatedUser
import com.anas.mealster.domain.model.User
import com.anas.mealster.domain.model.UserId
import com.anas.mealster.infra.database.entities.RefreshTokenEntity
import com.anas.mealster.infra.database.entities.UserEntity
import com.anas.mealster.infra.database.mappers.toUser
import com.anas.mealster.infra.database.repository.RefreshTokenRepository
import com.anas.mealster.infra.database.repository.UserRepository
import com.anas.mealster.infra.security.PasswordEncoder
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.time.Instant
import java.util.Base64

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JWTService,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val emailVerificationService: EmailVerificationService
) {

    @Transactional
    fun register(email: String, username: String, password: String): User {
        val trimmedEmail = email.trim()
        val user = userRepository.findByEmailOrUsername(
            email = trimmedEmail,
            username = username.trim()
        )
        if (user != null) {
            throw UserAlreadyExistException()
        }
        val savedUser = userRepository.save(
            UserEntity(
                email = trimmedEmail,
                username = username.trim(),
                hashedPassword = passwordEncoder.encode(password) ?: ""
            )
        ).toUser()
        val token = emailVerificationService.createEmailVerificationToken(trimmedEmail)
        return savedUser
    }

    @Transactional
    fun logout(
        refreshToken: String
    ) {
        val userId = jwtService.getUserIdFromToken(refreshToken)
        val hashed = hashedToken(refreshToken)
        refreshTokenRepository.deleteByUserIdAndHashedToken(
            userId = userId,
            hashedToken = hashed
        )
    }

    @Transactional
    fun refresh(refreshToken: String): AuthenticatedUser {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw InvalidTokenException("Invalid refresh token")
        }
        val userId = jwtService.getUserIdFromToken(refreshToken)
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException()
        val hashed = hashedToken(refreshToken)

        return user.id?.let { userId ->
            refreshTokenRepository.findByUserIdAndHashedToken(
                userId = userId,
                hashedToken = hashed
            )
            refreshTokenRepository.deleteByUserIdAndHashedToken(
                userId = userId,
                hashedToken = hashed
            )

            val newAccessToken = jwtService.generateAccessToken(userId)
            val newRefreshToken = jwtService.generateRefreshToken(userId)

            storeRefreshToken(userId = userId, rawRefreshToken = newRefreshToken)

            AuthenticatedUser(
                user = user.toUser(),
                accessToken = newAccessToken,
                refreshToken = newRefreshToken
            )

        } ?: throw UserNotFoundException()
    }

    fun login(
        email: String,
        password: String
    ): AuthenticatedUser {
        val user = userRepository.findByEmail(email) ?: throw InvalidCredentialException()
        if (!passwordEncoder.matches(password, user.hashedPassword)) {
            throw InvalidCredentialException()
        }
        //TODO check for email
        if (!user.hasVerifiedEmail){
            throw EmailNotVerifiedException()
        }

        return user.id?.let { userId ->
            val accessToken = jwtService.generateAccessToken(userId)
            val refreshToken = jwtService.generateRefreshToken(userId)

            storeRefreshToken(userId, refreshToken)
            AuthenticatedUser(
                user = user.toUser(),
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } ?: throw UserNotFoundException()
    }

    private fun storeRefreshToken(userId: UserId, rawRefreshToken: String) {
        val hashed = hashedToken(rawRefreshToken)
        val expiry = jwtService.refreshTokenValidityMs
        val expireAt = Instant.now().plusMillis(expiry)

        refreshTokenRepository.save(
            RefreshTokenEntity(
                userId = userId,
                expiresAt = expireAt,
                hashedToken = hashed
            )
        )
    }

    private fun hashedToken(rawRefreshToken: String): String {
        val digest = MessageDigest.getInstance("SHA256")
        val hashedBytes = digest.digest(rawRefreshToken.encodeToByteArray())
        return Base64.getEncoder().encodeToString(hashedBytes)
    }
}