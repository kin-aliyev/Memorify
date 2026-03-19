package com.example.feature_home.presentation.collection_detail.model

import com.example.core_domain.model.word.KnowledgeLevel

data class WordFilterState(
    val selectedLevels: Set<KnowledgeLevel> = emptySet(),
    val favoritesOnly: Boolean = false,
    val sortOption: WordSortOption = WordSortOption.NEWEST_FIRST
)

enum class WordSortOption {
    NEWEST_FIRST,
    OLDEST_FIRST,
    ALPHA_ASC,
    ALPHA_DESC,
    KNOWLEDGE_NEW_FIRST,
    KNOWLEDGE_KNOWN_FIRST,
}
