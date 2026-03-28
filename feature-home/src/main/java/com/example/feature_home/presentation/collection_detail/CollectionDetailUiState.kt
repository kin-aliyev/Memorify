package com.example.feature_home.presentation.collection_detail

import com.example.core_domain.model.collection.Collection
import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_domain.model.word.WordCard
import com.example.feature_home.presentation.collection_detail.model.WordFilterState
import com.example.feature_home.presentation.collection_detail.model.WordSortOption

data class CollectionDetailUiState(
    val isLoading: Boolean = true,
    val collection: Collection? = null,
    val words: List<WordCard> = emptyList(),
    val filterState: WordFilterState = WordFilterState(),
    val showTranslation: Boolean = true,
) {
    val filteredWords: List<WordCard> get() = words
        .let { list ->
            if (filterState.selectedLevels.isEmpty()) list
            else list.filter { word ->
                KnowledgeLevel.fromString(word.knowledgeLevel) in filterState.selectedLevels
            }
        }
        .let { list ->
            if (filterState.favoritesOnly) list.filter { it.isFavorite } else list
        }
        .let { list ->
            when(filterState.sortOption) {
                WordSortOption.NEWEST_FIRST -> list.sortedByDescending { it.createdAt }
                WordSortOption.OLDEST_FIRST -> list.sortedBy { it.createdAt }
                WordSortOption.ALPHA_ASC -> list.sortedBy { it.word.lowercase() }
                WordSortOption.ALPHA_DESC -> list.sortedByDescending { it.word.lowercase() }
                WordSortOption.KNOWLEDGE_NEW_FIRST -> list.sortedBy { word ->
                    KnowledgeLevel.fromString(word.knowledgeLevel).ordinal
                }
                WordSortOption.KNOWLEDGE_KNOWN_FIRST -> list.sortedByDescending { word ->
                    KnowledgeLevel.fromString(word.knowledgeLevel).ordinal
                }
            }
        }

    val newCount: Int get() = words.count { KnowledgeLevel.fromString(it.knowledgeLevel) == KnowledgeLevel.NEW }
    val learningCount: Int get() = words.count { KnowledgeLevel.fromString(it.knowledgeLevel) == KnowledgeLevel.LEARNING }
    val reviewingCount: Int get() = words.count { KnowledgeLevel.fromString(it.knowledgeLevel) == KnowledgeLevel.REVIEWING }
    val masteredCount: Int get() = words.count { KnowledgeLevel.fromString(it.knowledgeLevel) == KnowledgeLevel.KNOWN }
}
