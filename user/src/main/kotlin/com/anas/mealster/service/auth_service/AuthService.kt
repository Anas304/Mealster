package com.anas.mealster.service.auth_service

import com.anas.mealster.domain.exception.UserAlreadyExistException
import com.anas.mealster.domain.model.User
import com.anas.mealster.infra.database.entities.UserEntity
import com.anas.mealster.infra.database.mappers.toUser
import com.anas.mealster.infra.database.repository.UserRepository
import com.anas.mealster.infra.security.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun register(email: String, username: String, password: String): User {
        val user = userRepository.findByEmailOrUsername(
            email = email.trim(),
            username = username.trim()
        )
        if (user !=null){
            throw UserAlreadyExistException()
        }
        val savedUser = userRepository.save(
            UserEntity(
                email = email,
                username = username,
                hashedPassword = passwordEncoder.encode(password) ?: ""
            )
        ).toUser()
        return savedUser
    }
}