package com.example.feature_auth.domain.usecase.auth

import com.example.core_domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Result<Unit> = authRepository.signOut()
}