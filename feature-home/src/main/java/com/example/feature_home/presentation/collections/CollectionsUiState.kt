package com.example.feature_home.presentation.collections

import com.example.feature_home.presentation.model.DeckUiModel

data class CollectionsUiState(
    val decks: List<DeckUiModel> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)
