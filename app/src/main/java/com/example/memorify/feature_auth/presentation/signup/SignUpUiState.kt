package com.example.memorify.feature_auth.presentation.signup

import com.example.memorify.feature_auth.presentation.model.PasswordRuleUi

data class SignUpUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isConfirmPasswordValid: Boolean = true,
    val isEmailTouched: Boolean = false,
    val passwordRules: List<PasswordRuleUi> = emptyList(),
    val navigationEvent: SignUpNavigationEvent? = null,
) {
    val showEmailError: Boolean
        get() = isEmailTouched && !isEmailValid && email.isNotBlank()

    val showPasswordError: Boolean
        get() = !isPasswordValid && password.isNotBlank()

    val hasError: Boolean
        get() = errorMessage != null

    val canSignUp: Boolean
        get() = email.isNotBlank() && isEmailValid &&
                password.isNotBlank() && isPasswordValid &&
                confirmPassword.isNotBlank() && isConfirmPasswordValid
}

sealed interface SignUpNavigationEvent{
    data object NavigateToHome : SignUpNavigationEvent
}