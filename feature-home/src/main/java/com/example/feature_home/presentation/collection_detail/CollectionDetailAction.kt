package com.example.feature_home.presentation.collection_detail

sealed interface CollectionDetailAction {
    data object OnToggleTranslation : CollectionDetailAction
}