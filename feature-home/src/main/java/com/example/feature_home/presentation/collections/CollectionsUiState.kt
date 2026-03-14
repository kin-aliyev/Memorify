package com.example.feature_home.presentation.collections

import com.example.feature_home.presentation.model.CollectionUiModel

data class CollectionsUiState(
    val collections: List<CollectionUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
