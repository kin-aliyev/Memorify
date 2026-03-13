package com.example.feature_auth.presentation.signin

import com.example.feature_auth.presentation.model.PasswordRuleUi

data class SignInUiState(
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isEmailTouched: Boolean = false,
    val passwordRules: List<PasswordRuleUi> = emptyList(),
) {
    val showEmailError: Boolean
        get() = isEmailTouched && !isEmailValid && email.isNotBlank()

    val showPasswordError: Boolean
        get() = !isPasswordValid && password.isNotBlank()

    val hasError: Boolean
        get() = errorMessage != null

    val canSignIn: Boolean
        get() = email.isNotBlank() && isEmailValid && password.isNotBlank() && isPasswordValid
}
