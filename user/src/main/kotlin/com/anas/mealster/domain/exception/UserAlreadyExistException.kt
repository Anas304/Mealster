package com.anas.mealster.domain.exception

import java.lang.Exception

class UserAlreadyExistException : Exception(
    "A user with this username or email already exists."
)