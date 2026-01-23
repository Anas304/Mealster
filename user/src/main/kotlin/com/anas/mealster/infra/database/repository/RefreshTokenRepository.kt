package com.anas.mealster.infra.database.repository

import com.anas.mealster.domain.model.UserId
import com.anas.mealster.infra.database.entities.RefreshTokenEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenRepository : JpaRepository<RefreshTokenEntity, Long>{

    fun findByUserIdAndHashedToken(userId: UserId,hashedToken: String) : RefreshTokenEntity?
    fun deleteByUserIdAndHashedToken(userId: UserId,hashedToken: String)
    fun deleteByUserId(userId: UserId)
}