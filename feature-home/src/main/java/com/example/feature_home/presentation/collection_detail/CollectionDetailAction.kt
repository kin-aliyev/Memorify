package com.example.feature_home.presentation.collection_detail

import com.example.core_domain.model.collection.CollectionColor
import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_domain.model.word.WordCard
import com.example.feature_home.presentation.collection_detail.model.WordSortOption

sealed interface CollectionDetailAction {
    // Filter
    data object OnClearFilters : CollectionDetailAction
    data class OnKnowledgeFilterToggle(val level: KnowledgeLevel) : CollectionDetailAction
    data object OnFavoritesFilterToggle : CollectionDetailAction
    data class OnSortOptionSelect(val option: WordSortOption) : CollectionDetailAction

    // Word
    data class OnWordFavoriteToggle(val word: WordCard) : CollectionDetailAction
    data class OnDeleteWord(val word: WordCard) : CollectionDetailAction
    data class OnEditWord(val word: WordCard) : CollectionDetailAction

    // Translation
    data object OnTranslationVisibilityToggled : CollectionDetailAction

    // FAB
    data object OnAddWordManualClick : CollectionDetailAction
    data object OnAddWordAiClick : CollectionDetailAction

    // Collection
    data object OnRetry : CollectionDetailAction
    data class OnEditCollectionConfirm(
        val name: String, val emoji: String, val color: CollectionColor
    ): CollectionDetailAction
    data object OnDeleteCollection : CollectionDetailAction
}