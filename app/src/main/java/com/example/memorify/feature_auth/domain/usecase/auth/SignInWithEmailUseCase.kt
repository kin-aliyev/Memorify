package com.example.memorify.feature_auth.domain.usecase.auth

import com.example.memorify.core.domain.model.User
import com.example.memorify.feature_auth.domain.exception.AuthException
import com.example.memorify.feature_auth.domain.repository.AuthRepository
import com.example.memorify.feature_auth.domain.validation.EmailValidator
import com.example.memorify.feature_auth.domain.validation.PasswordValidator
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        // Early validation for empty fields
        if (email.isBlank()) return Result.failure(AuthException.EmptyEmail)
        if (password.isBlank()) return Result.failure(AuthException.EmptyPassword)

        // Validate email format
        if (!emailValidator.isValid(email)) return Result.failure(AuthException.InvalidEmail)

        // Validate password format
        if (!passwordValidator.validate(password).isValid) return Result.failure(AuthException.WeakPassword)

        return authRepository.signInWithEmail(email, password)
    }
}