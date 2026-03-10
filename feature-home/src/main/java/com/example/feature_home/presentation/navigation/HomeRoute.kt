package com.example.feature_home.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface HomeRoute {
    @Serializable
    data object Home : HomeRoute

    @Serializable
    data class Detail(val id: String) : HomeRoute
}