package com.example.feature_auth.presentation.signup

sealed interface SignUpAction {
    data class UpdateEmail(val email: String) : SignUpAction
    data class UpdatePassword(val password: String) : SignUpAction
    data class UpdateConfirmPassword(val confirmPassword: String) : SignUpAction

    data object EmailFocusLost : SignUpAction

    data object SignUpWithEmail : SignUpAction
}