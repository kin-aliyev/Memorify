package com.example.feature_auth.presentation.signin

import android.content.Context

sealed interface SignInAction {
    data class UpdateEmail(val email: String) : SignInAction
    data class UpdatePassword(val password: String) : SignInAction

    data object SignInWithEmail : SignInAction
    data class SignInWithGoogle(val context: Context) : SignInAction

    data object EmailFocusLost: SignInAction

    data object ForgotPassword : SignInAction
    data object CreateAccount : SignInAction
}