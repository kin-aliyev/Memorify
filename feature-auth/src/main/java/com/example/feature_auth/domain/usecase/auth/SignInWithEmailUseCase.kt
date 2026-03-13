package com.example.feature_auth.domain.usecase.auth

import com.example.core_domain.model.User
import com.example.core_domain.exception.AppException
import com.example.core_domain.repository.AuthRepository
import com.example.feature_auth.domain.validation.EmailValidator
import com.example.feature_auth.domain.validation.PasswordValidator
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        // Early validation for empty fields
        if (email.isBlank()) return Result.failure(AppException.EmptyEmail)
        if (password.isBlank()) return Result.failure(AppException.EmptyPassword)

        // Validate email format
        if (!emailValidator.isValid(email)) return Result.failure(AppException.InvalidEmail)

        // Validate password format
        if (!passwordValidator.validate(password).isValid) return Result.failure(AppException.WeakPassword)

        return authRepository.signInWithEmail(email, password)
    }
}