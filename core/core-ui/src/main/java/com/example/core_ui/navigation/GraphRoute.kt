package com.example.core_ui.navigation

import kotlinx.serialization.Serializable

sealed interface GraphRoute {
    @Serializable
    data object Auth : GraphRoute

    @Serializable
    data object Home : GraphRoute

    @Serializable
    data object Analytics: GraphRoute

    @Serializable
    data object Settings: GraphRoute
}




