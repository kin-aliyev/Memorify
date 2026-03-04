package com.example.feature_auth.domain.usecase.auth

import com.example.core_domain.model.User
import com.example.core_domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCurrentUserStateUseCase  @Inject constructor(
    private val authRepository: AuthRepository
){
    operator fun invoke(): Flow<User?> = authRepository.currentUser
}