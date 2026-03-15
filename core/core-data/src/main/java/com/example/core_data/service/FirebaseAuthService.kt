package com.example.core_data.service

import com.example.core_data.mapper.mapException
import com.example.core_domain.exception.AppException
import com.google.firebase.auth.FirebaseAuth
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
        val listener = FirebaseAuth.AuthStateListener { auth -> trySend(auth.currentUser) }
        firebaseAuth.addAuthStateListener(listener)

        awaitClose { firebaseAuth.removeAuthStateListener(listener) }
    }

    suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser> =
        runCatching {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            authResult.user ?: throw AppException.UnknownError(errorMessage = "User is null")
        }.mapException()


    suspend fun signUpWithEmail(email: String, password: String): Result<FirebaseUser> =
        runCatching {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            authResult.user ?: throw AppException.UnknownError(errorMessage = "User is null")
        }.mapException()


    suspend fun signInWithGoogle(googleIdToken: String): Result<FirebaseUser> =
        runCatching {
            val credential = GoogleAuthProvider.getCredential(googleIdToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()

            authResult.user ?: throw AppException.UnknownError(errorMessage = "User is null")
        }.mapException()


    suspend fun deleteAccount(): Result<Unit> = runCatching {
        firebaseAuth.currentUser?.delete()?.await() ?: throw AppException.UserNotFound
        Unit
    }.mapException()


    fun signOut(): Result<Unit> = runCatching { firebaseAuth.signOut() }.mapException()

}