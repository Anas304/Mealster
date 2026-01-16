package com.anas.mealster.service

import com.anas.mealster.domain.exception.InvalidCredentialException
import com.anas.mealster.domain.exception.InvalidTokenException
import com.anas.mealster.domain.exception.SamePasswordException
import com.anas.mealster.domain.exception.UserNotFoundException
import com.anas.mealster.domain.model.UserId
import com.anas.mealster.infra.database.entities.PasswordResetTokenEntity
import com.anas.mealster.infra.database.repository.PasswordResetTokenRepository
import com.anas.mealster.infra.database.repository.RefreshTokenRepository
import com.anas.mealster.infra.database.repository.UserRepository
import com.anas.mealster.infra.security.PasswordEncoder
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class PasswordResetService(
    private val passwordResetTokenRepository: PasswordResetTokenRepository,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val refreshTokenRepository: RefreshTokenRepository,
    @param:Value("\${mealster.email.password-reset.expiry-minutes}")
    private val expiryMinutes: Long
) {

    fun resetPasswordRequest(email: String) {
        val user = userRepository.findByEmail(email) ?: return
        passwordResetTokenRepository.invalidateActiveUserToken(user)

        val token = PasswordResetTokenEntity(
            expiresAt = Instant.now().plus(expiryMinutes, ChronoUnit.MINUTES),
            user = user
        )
        passwordResetTokenRepository.save(token)
        //TODO:  inform notification service about password reset trigger to send email
    }

    fun resetPassword(token: String, newPassword: String) {
        val resetToken = passwordResetTokenRepository.findByToken(token)
            ?: throw InvalidTokenException("Invalid password reset token.")
        if (resetToken.isUsed) {
            throw InvalidTokenException("Email verification token is already used.")
        }

        if (resetToken.isExpired) {
            throw InvalidTokenException("Email verification token is already expired.")
        }

        val user = resetToken.user

        if (passwordEncoder.matches(newPassword, user.hashedPassword)) {
            throw SamePasswordException()
        }

        val hashedNewPassword = passwordEncoder.encode(newPassword)

        userRepository.save(
            user.apply {
                this.hashedPassword = hashedNewPassword
            }
        )

        //invalidate password reset token because it's no longer necessary
        passwordResetTokenRepository.save(
            resetToken.apply {
                this.usedAt = Instant.now()
            }
        )
        // invalidating refresh tokens in our DB as password is now reset
        refreshTokenRepository.deleteById(user.id!!)
    }

    fun changePassword(
        userId: UserId,
        oldPassword: String,
        newPassword: String
    ) {
        val user = userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException()
        if (!passwordEncoder.matches(newPassword,user.hashedPassword)){
            throw InvalidCredentialException()
        }
        if (oldPassword == newPassword){
            throw SamePasswordException()
        }
        refreshTokenRepository.deleteById(user.id!!)
        val newHashedPassword = passwordEncoder.encode(newPassword)

        userRepository.save(
            user.apply {
                this.hashedPassword = newHashedPassword
            }
        )
    }

    @Scheduled(cron = "0 0 3 * * *")// this means cleanup expired tokens every day at 3:00 am
    fun cleanupExpiredTokens() {
        passwordResetTokenRepository.deleteByExpiresAtLessThan(
            now = Instant.now()
        )
    }
}



