package com.anas.mealster.infra.database.mappers

import com.anas.mealster.domain.model.User
import com.anas.mealster.infra.database.entities.UserEntity

fun UserEntity.toUser() : User {
    /** !! this is a strong promise that at this point of code the user id won't be nullable because the moment we add a user
     * in AuthService successfully then perform a mapper on it, then that means the id does exist in our DB*/
    return User(
        id = id!!,
        email = email,
        username = username,
        password = hashedPassword,
        hasEmailVerified = hasVerifiedEmail
    )
}