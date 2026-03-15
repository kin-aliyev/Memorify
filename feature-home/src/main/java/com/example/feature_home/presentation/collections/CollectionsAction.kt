package com.example.feature_home.presentation.collections

import com.example.core_domain.model.collection.CollectionColor

sealed interface CollectionsAction {
    data class OnCollectionClick(val collectionId: String) : CollectionsAction

    data class OnAddCollectionConfirm(
        val name: String, val emoji: String, val color: CollectionColor,
    ) : CollectionsAction

    data object OnAddWordManualClick : CollectionsAction
    data object OnAddWordAiClick : CollectionsAction
}