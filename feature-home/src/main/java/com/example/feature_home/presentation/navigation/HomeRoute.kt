package com.example.feature_home.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface HomeRoute {
    @Serializable
    data object Collections : HomeRoute

    @Serializable
    data class CollectionDetail(val collectionId: String) : HomeRoute

    @Serializable
    data class EditCollection(val collectionId: String) : HomeRoute

    @Serializable
    data class AddEditWord(val collectionId: String, val wordId: String? = null) : HomeRoute

    @Serializable
    data class AddWordAi(val collectionId: String) : HomeRoute
}