package com.example.feature_home.presentation.collection_detail

sealed interface CollectionDetailError {
    data object LoadFailed : CollectionDetailError
    data object DeleteWordFailed : CollectionDetailError
    data object FavoriteUpdateFailed : CollectionDetailError
    data object DeleteCollectionFailed : CollectionDetailError
}