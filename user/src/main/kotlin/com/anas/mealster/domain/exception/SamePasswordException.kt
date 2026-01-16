package com.anas.mealster.domain.exception

class SamePasswordException : RuntimeException(
    "The new password can not be same as old password "
)