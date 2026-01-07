package com.anas.mealster.domain.exception

import java.lang.RuntimeException

class InvalidCredentialException : RuntimeException(
    "A user with this email or password already exists."
)