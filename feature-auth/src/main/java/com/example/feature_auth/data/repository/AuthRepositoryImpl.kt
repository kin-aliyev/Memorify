package com.example.feature_auth.data.repository

import com.example.core_domain.model.User
import com.example.feature_auth.data.remote.mapper.toDomain
import com.example.feature_auth.data.remote.service.FirebaseAuthService
import com.example.core_domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuthService: FirebaseAuthService
): AuthRepository {

    override val currentUser: Flow<User?> = firebaseAuthService.currentUser.map { it?.toDomain() }

    override suspend fun signInWithEmail(email: String, password: String): Result<User> =
        firebaseAuthService.signInWithEmail(email, password).map { it.toDomain() }

    override suspend fun signUpWithEmail(email: String, password: String ): Result<User> =
        firebaseAuthService.signUpWithEmail(email, password).map { it.toDomain() }
    override suspend fun signInWithGoogle(googleIdToken: String): Result<User> =
        firebaseAuthService.signInWithGoogle(googleIdToken).map { it.toDomain() }

    override fun signOut(): Result<Unit> = firebaseAuthService.signOut()

    override suspend fun deleteAccount(): Result<Unit> = firebaseAuthService.deleteAccount()

}