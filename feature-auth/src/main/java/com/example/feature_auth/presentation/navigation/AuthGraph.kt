package com.example.feature_auth.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AuthGraph {
    @Serializable
    data object SignIn : AuthGraph

    @Serializable
    data object SignUp : AuthGraph

    @Serializable
    data object ForgotPassword : AuthGraph
}