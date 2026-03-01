package com.example.memorify.feature_auth.presentation.signin

import com.example.memorify.feature_auth.presentation.model.PasswordRuleUi

data class SignInUiState(
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = true,
    val isPasswordValid: Boolean = true,
    val isEmailTouched: Boolean = false,
    val passwordRules: List<PasswordRuleUi> = emptyList(),
    val navigationEvent: SignInNavigationEvent? = null,
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



sealed interface SignInNavigationEvent {
    data object NavigateToForgotPassword : SignInNavigationEvent
    data object NavigateToSignUp : SignInNavigationEvent
    data object NavigateToHome : SignInNavigationEvent
}
