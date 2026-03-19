package com.example.feature_home.presentation.collection_detail

sealed interface CollectionDetailNavigationEvent {
    data class ToEditWord(val collectionId: String, val wordId: String) : CollectionDetailNavigationEvent
    data class ToAddWord(val collectionId: String) : CollectionDetailNavigationEvent
    data object ToEditCollection : CollectionDetailNavigationEvent
    data object Back : CollectionDetailNavigationEvent
}