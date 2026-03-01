package com.example.memorify.core.navigation

import kotlinx.serialization.Serializable

sealed interface GraphRoute {
    @Serializable
    data object Auth : GraphRoute

    @Serializable
    data object Main : GraphRoute
}




