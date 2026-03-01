package com.example.memorify.feature_auth.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AuthRoute {
    @Serializable
    data object SignIn : AuthRoute

    @Serializable
    data object SignUp : AuthRoute

    @Serializable
    data object ForgotPassword : AuthRoute
}