package com.example.feature_auth.domain.exception

sealed class AuthException() : Exception() {
    // Firebase/Network exceptions
    object InvalidCredentials : AuthException()
    object UserNotFound : AuthException()
    object UserAlreadyExists : AuthException()
    object NetworkError : AuthException()
    object ReAuthRequired : AuthException()

    // Google-specific exceptions
    object GoogleAccountNotFound: AuthException()
    object GoogleAuthCancelled: AuthException()
    object GoogleAuthFailed: AuthException()

    // Validation exceptions (more specific)
    object InvalidEmail : AuthException()
    object WeakPassword: AuthException()
    object PasswordMismatch : AuthException()
    object EmptyEmail : AuthException()
    object EmptyPassword : AuthException()

    data class UnknownError(
        val originalException: Throwable? = null,
        val errorMessage: String? = null
    ) : AuthException()
}