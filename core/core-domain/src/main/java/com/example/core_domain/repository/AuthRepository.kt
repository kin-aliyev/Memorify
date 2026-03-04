package com.example.core_domain.repository

import com.example.core_domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>

    suspend fun signInWithEmail(email: String, password: String): Result<User>
    suspend fun signUpWithEmail(email: String, password: String): Result<User>
    suspend fun signInWithGoogle(googleIdToken: String): Result<User>
    fun signOut(): Result<Unit>
    suspend fun deleteAccount(): Result<Unit>
}