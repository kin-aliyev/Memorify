package com.example.core_data.mapper

import com.example.core_domain.exception.AppException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.firestore.FirebaseFirestoreException
import java.io.IOException

internal fun Throwable.toAppException(): AppException = when (this) {
    is AppException -> this
    is FirebaseFirestoreException -> when(code) {
        FirebaseFirestoreException.Code.UNAVAILABLE, FirebaseFirestoreException.Code.DEADLINE_EXCEEDED -> AppException.NetworkError
        FirebaseFirestoreException.Code.PERMISSION_DENIED -> AppException.UserNotFound
        else -> AppException.UnknownError(this)
    }
    is FirebaseNetworkException -> AppException.NetworkError
    is IOException -> AppException.NetworkError
    else -> AppException.UnknownError(this)
}

internal fun <T> Result<T>.mapException(): Result<T> = this.recoverCatching { throw it.toAppException() }