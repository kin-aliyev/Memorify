package com.example.feature_auth.presentation.signin

sealed interface SignInNavigationEvent {
    data object ToHome : SignInNavigationEvent
    data object ToForgotPassword : SignInNavigationEvent
    data object ToSignUp : SignInNavigationEvent
}