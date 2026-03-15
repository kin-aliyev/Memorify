package com.example.core_data.mapper

import com.example.core_domain.exception.AppException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestoreException
import java.io.IOException

internal fun Throwable.toAppException(): AppException = when (this) {
    is AppException -> this

    // Shared
    is FirebaseNetworkException,
    is IOException -> AppException.NetworkError

    // Auth-specific
    is FirebaseAuthInvalidCredentialsException -> AppException.InvalidCredentials
    is FirebaseAuthInvalidUserException -> AppException.UserNotFound
    is FirebaseAuthUserCollisionException -> AppException.UserAlreadyExists
    is FirebaseAuthRecentLoginRequiredException -> AppException.ReAuthRequired

    // Firestore-specific
    is FirebaseFirestoreException -> when (code) {
        FirebaseFirestoreException.Code.UNAVAILABLE,
        FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> AppException.NetworkError
        FirebaseFirestoreException.Code.PERMISSION_DENIED -> AppException.UserNotFound
        else -> AppException.UnknownError(this)
    }

    else -> AppException.UnknownError(this)
}

internal fun <T> Result<T>.mapException(): Result<T> = recoverCatching { throw it.toAppException() }

