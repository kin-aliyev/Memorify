package com.example.feature_home.presentation.collection_detail

import com.example.core_domain.model.collection.Collection
import com.example.core_domain.model.word.WordCard

data class CollectionDetailUiState(
    val collection: Collection? = null,
    val words: List<WordCard> = emptyList(),
//    val filterState: WordFilterState = WordFilterState(),
    val showTranslation: Boolean = true,
    val isLoading: Boolean = false
)
