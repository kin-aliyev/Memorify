package com.example.memorify.feature_auth.domain.usecase.auth

import com.example.memorify.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Result<Unit> = authRepository.signOut()
}