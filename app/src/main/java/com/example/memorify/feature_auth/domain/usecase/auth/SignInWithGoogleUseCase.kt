package com.example.memorify.feature_auth.domain.usecase.auth

import android.content.Context
import com.example.memorify.core.domain.model.User
import com.example.memorify.feature_auth.domain.provider.GoogleTokenProvider
import com.example.memorify.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignInWithGoogleUseCase @Inject constructor(
    private val googleTokenProvider: GoogleTokenProvider,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(context: Context): Result<User> {
        val token = googleTokenProvider.getIdToken(context)
            .getOrElse { error -> return Result.failure(error) }

        return authRepository.signInWithGoogle(token)
    }
}