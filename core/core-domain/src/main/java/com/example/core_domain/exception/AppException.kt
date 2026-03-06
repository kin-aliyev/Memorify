package com.example.core_domain.exception

sealed class AppException() : Exception() {
    // Firebase/Network exceptions
    object InvalidCredentials : AppException()
    object UserNotFound : AppException()
    object UserAlreadyExists : AppException()
    object NetworkError : AppException()
    object ReAuthRequired : AppException()

    // Google-specific exceptions
    object GoogleAccountNotFound: AppException()
    object GoogleAuthCancelled: AppException()
    object GoogleAuthFailed: AppException()

    // Validation exceptions (more specific)
    object InvalidEmail : AppException()
    object WeakPassword: AppException()
    object PasswordMismatch : AppException()
    object EmptyEmail : AppException()
    object EmptyPassword : AppException()

    data class UnknownError(
        val originalException: Throwable? = null,
        val errorMessage: String? = null
    ) : AppException()
}