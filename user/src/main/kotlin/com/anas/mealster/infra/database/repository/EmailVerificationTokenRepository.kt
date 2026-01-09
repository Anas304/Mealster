package com.anas.mealster.infra.database.repository

import com.anas.mealster.infra.database.entities.EmailVerificationTokenEntity
import com.anas.mealster.infra.database.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

interface EmailVerificationTokenRepository : JpaRepository<EmailVerificationTokenEntity, Long> {

    fun findByToken(token: String) : EmailVerificationTokenRepository?
    fun deleteByExpiresAtLessThan(now: Instant)
    fun findByUserAndUserAtIsNull(user: UserEntity) : List<EmailVerificationTokenRepository>

}