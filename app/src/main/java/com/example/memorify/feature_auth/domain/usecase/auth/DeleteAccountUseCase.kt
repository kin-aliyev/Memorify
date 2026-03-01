package com.example.memorify.feature_auth.domain.usecase.auth

import com.example.memorify.feature_auth.domain.repository.AuthRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> = authRepository.deleteAccount()
}