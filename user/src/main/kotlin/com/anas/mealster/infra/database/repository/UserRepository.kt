package com.anas.mealster.infra.database.repository

import com.anas.mealster.domain.model.UserId
import com.anas.mealster.infra.database.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, UserId>{

    fun findByEmail(email: String) : UserEntity?
    fun findByEmailOrUsername(email: String,username: String) : UserEntity?
}