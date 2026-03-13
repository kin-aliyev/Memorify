package com.example.feature_home.presentation.collections

sealed interface CollectionsNavigationEvent {
    data class ToCollectionDetail(val deckId: String) : CollectionsNavigationEvent

    data object ToAddCollection : CollectionsNavigationEvent
    data object ToAddManual : CollectionsNavigationEvent
    data object ToAddAi : CollectionsNavigationEvent
}