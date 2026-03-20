package com.example.feature_home.presentation.collection_detail

import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_domain.model.word.WordCard
import com.example.feature_home.presentation.collection_detail.model.WordSortOption

sealed interface CollectionDetailAction {
    // Filter
    data class OnLevelFilterToggle(val level: KnowledgeLevel) : CollectionDetailAction
    data class OnSortOptionSelect(val option: WordSortOption) : CollectionDetailAction
    data object OnFavoritesToggle : CollectionDetailAction
    data object OnClearFilters : CollectionDetailAction

    // Word
    data class OnFavoriteToggle(val word: WordCard) : CollectionDetailAction
    data class OnDeleteWord(val word: WordCard) : CollectionDetailAction
    data class OnEditWord(val word: WordCard) : CollectionDetailAction

    // Translation
    data object OnToggleTranslation : CollectionDetailAction

    // FAB
    data object OnAddWordManual : CollectionDetailAction
    data object OnAddWordAi : CollectionDetailAction

    data object OnEditCollection : CollectionDetailAction
    data object OnDeleteCollection : CollectionDetailAction
}