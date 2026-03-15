package com.example.feature_home.presentation.collections

sealed interface CollectionsNavigationEvent {
    data class ToCollectionDetail(val collectionId: String) : CollectionsNavigationEvent

    data object ToAddManual : CollectionsNavigationEvent
    data object ToAddAi : CollectionsNavigationEvent
}