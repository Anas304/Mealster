package com.anas.mealster.api.exception

import com.anas.mealster.domain.exception.InvalidCredentialException
import com.anas.mealster.domain.exception.InvalidTokenException
import com.anas.mealster.domain.exception.UserAlreadyExistException
import com.anas.mealster.domain.exception.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
/**
 * Global Exception Handler for Authentication and User management.
 * * Annotated with @RestControllerAdvice, this class acts as an interceptor that catches
 * specific exceptions thrown by any @RestController across the application.
 * It centralizes error logic, ensuring that instead of a raw stack trace, the client
 * receives a consistent, structured JSON response (e.g., key-value pairs) and
 * the appropriate HTTP status code.
 */
@RestControllerAdvice
class AuthExceptionHandler {

    @ExceptionHandler(InvalidCredentialException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun onInvalidCredentials(
        e: InvalidCredentialException
    ) = mapOf(
        "code" to "INVALID_CREDENTIALS",
        "message" to e.message
    )

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND )
    fun onUserNotFound(
        e: UserNotFoundException
    ) = mapOf(
        "code" to "USER_NOT_FOUND ",
        "message" to e.message
    )

    @ExceptionHandler(InvalidTokenException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun onUserAlreadyExistException(
        e : InvalidTokenException
    ) = mapOf(
        "code" to "INVALID_TOKEN",
        "message" to e.message
    )

    @ExceptionHandler(UserAlreadyExistException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun onUserAlreadyExistException(
        e : UserAlreadyExistException
    ) = mapOf(
        "code" to "User_Exists",
        "message" to e.message
    )

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun onValidationException(
        e: MethodArgumentNotValidException
    ): ResponseEntity<Map<String, Any>> {
        val errors = e.bindingResult.allErrors.map {
            it.defaultMessage ?: "Invalid value"
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                mapOf(
                    "code" to "VALIDATION_ERROR",
                    "errors" to errors
                )
            )
    }
}