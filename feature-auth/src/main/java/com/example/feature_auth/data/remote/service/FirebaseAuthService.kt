package com.example.feature_auth.data.remote.service

import com.example.feature_auth.domain.exception.AuthException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) {
    val currentUser: Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser)
        }
        firebaseAuth.addAuthStateListener(listener)

        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user
                ?: return Result.failure(AuthException.UnknownError(errorMessage = "Authentication succeeded but user is null"))

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(mapToAuthException(e, "Sign in failed"))
        }
    }

    suspend fun signUpWithEmail(email: String, password: String): Result<FirebaseUser> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user
                ?: return Result.failure(AuthException.UnknownError(errorMessage = "Account creation succeeded but user is null"))

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(mapToAuthException(e, "Sign up failed"))
        }
    }

    suspend fun signInWithGoogle(googleIdToken: String): Result<FirebaseUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            val user = authResult.user
                ?: return Result.failure(AuthException.UnknownError(errorMessage = "Google authentication succeeded but user is null"))

            Result.success(user)
        } catch (e: Exception) {
            Result.failure(mapToAuthException(e, "Google sign in failed"))
        }
    }

    suspend fun deleteAccount(): Result<Unit> {
        return try {
            val currentUser = firebaseAuth.currentUser ?: return Result.failure(AuthException.UserNotFound)
            currentUser.delete().await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(mapToAuthException(e, "Failed to delete account"))
        }
    }

    fun signOut(): Result<Unit> {
        return try {
            firebaseAuth.signOut()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(mapToAuthException(e, "Sign out failed"))
        }
    }

    private fun mapToAuthException(e: Exception, defaultMessage: String): AuthException {
        return when (e) {
            is AuthException -> e
            is FirebaseAuthInvalidCredentialsException -> AuthException.InvalidCredentials
            is FirebaseAuthInvalidUserException -> AuthException.UserNotFound
            is FirebaseAuthUserCollisionException -> AuthException.UserAlreadyExists
            is FirebaseAuthRecentLoginRequiredException -> AuthException.ReAuthRequired
            is FirebaseNetworkException -> AuthException.NetworkError

            else -> AuthException.UnknownError(e, defaultMessage)
        }
    }
}