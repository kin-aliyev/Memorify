package com.example.feature_home.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface HomeGraph {
    @Serializable
    data object Collections : HomeGraph

    @Serializable
    data class CollectionDetail(val collectionId: String) : HomeGraph

    @Serializable
    data class AddEditWord(val collectionId: String, val wordId: String? = null) : HomeGraph

    @Serializable
    data class AddWordAi(val collectionId: String) : HomeGraph
}