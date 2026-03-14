package com.example.feature_home.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface HomeRoute {
    @Serializable
    data object Collections : HomeRoute

    @Serializable
    data class CollectionDetail(val collectionId: String) : HomeRoute

    @Serializable
    data object AddCollection : HomeRoute

    @Serializable
    data class AddWordManual(val collectionId: String) : HomeRoute

    @Serializable
    data class AddWordAi(val collectionId: String) : HomeRoute
}