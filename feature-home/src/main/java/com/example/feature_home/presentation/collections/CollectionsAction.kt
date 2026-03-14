package com.example.feature_home.presentation.collections

sealed interface CollectionsAction {
    data class OnCollectionClick(val collectionId: String) : CollectionsAction
    data object OnAddCollectionClick : CollectionsAction
    data object OnAddWordManualClick : CollectionsAction
    data object OnAddWordAiClick : CollectionsAction
//    data object OnErrorDismiss : CollectionsAction
}