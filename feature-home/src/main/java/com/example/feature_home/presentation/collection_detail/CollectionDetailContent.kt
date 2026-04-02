package com.example.feature_home.presentation.collection_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryAdd
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_domain.model.collection.Collection
import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_domain.model.word.WordCard
import com.example.core_ui.Dimens
import com.example.core_ui.common.ErrorRetryState
import com.example.core_ui.common.scaffold.AppHeader
import com.example.core_ui.common.scaffold.PreviewScaffold
import com.example.core_ui.model.BottomNavItem
import com.example.core_ui.model.TopBarState
import com.example.core_ui.theme.MemorifyTheme
import com.example.feature_home.presentation.collection_detail.components.CollectionDetailSkeleton
import com.example.feature_home.presentation.collection_detail.components.CollectionSummaryCard
import com.example.feature_home.presentation.collection_detail.components.filter_bar.FilterBar
import com.example.feature_home.presentation.collection_detail.components.word_item.WordItem
import com.example.feature_home.presentation.collection_detail.model.WordFilterState

@Composable
fun CollectionDetailContent(
    modifier: Modifier = Modifier,
    uiState: CollectionDetailUiState,
    onAction: (CollectionDetailAction) -> Unit,
    onSortClick: () -> Unit,
) {
    when {
        uiState.isLoading -> {
            CollectionDetailSkeleton(modifier = modifier)
        }

        uiState.isError -> {
            ErrorRetryState(
                onRetry = { onAction(CollectionDetailAction.OnRetry) },
                modifier = modifier
            )
        }

        else -> {
            val collection = uiState.collection ?: return

            LazyColumn(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacing12),
                contentPadding = PaddingValues(horizontal = Dimens.paddingScreen)
            ) {
                item(key = "summary_card") {
                    CollectionSummaryCard(
                        collection = collection,
                        newCount = uiState.newCount,
                        learningCount = uiState.learningCount,
                        reviewingCount = uiState.reviewingCount,
                        masteredCount = uiState.masteredCount,
                    )
                }

                stickyHeader(key = "filter_bar") {
                    Surface(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FilterBar(
                            filterState = uiState.filterState,
                            onClearFilters = { onAction(CollectionDetailAction.OnClearFilters) },
                            onLevelToggle = {
                                onAction(
                                    CollectionDetailAction.OnKnowledgeFilterToggle(
                                        it
                                    )
                                )
                            },
                            onFavoritesToggle = { onAction(CollectionDetailAction.OnFavoritesFilterToggle) },
                            onSortClick = onSortClick
                        )
                    }
                }

                if (uiState.filteredWords.isEmpty()) {
                    item(key = "empty_state") {
                        EmptyWordsState(
                            isFiltered = uiState.filterState.isActive,
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                } else {
                    items(
                        items = uiState.filteredWords,
                        key = { it.id }
                    ) { word ->
                        WordItem(
                            word = word,
                            showTranslation = uiState.showTranslation,
                            onFavoriteToggle = {
                                onAction(
                                    CollectionDetailAction.OnWordFavoriteToggle(
                                        word
                                    )
                                )
                            },
                            onEditClick = { onAction(CollectionDetailAction.OnEditWord(word)) },
                            onDeleteClick = { onAction(CollectionDetailAction.OnDeleteWord(word)) },
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun EmptyWordsState(
    isFiltered: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.height(Dimens.heightEmptyState),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing8),
        ) {
            Icon(
                imageVector = if (isFiltered) Icons.Default.SearchOff else Icons.Default.LibraryAdd,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                modifier = Modifier.size(Dimens.iconXxl),
            )
            Text(
                text = if (isFiltered) "No words match\nyour current filters"
                else "No word cards\nClick \"+\" to add one",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

// ── Preview helpers ───────────────────────────────────────────────────────────

private fun previewCollection() = Collection(
    id = "preview",
    name = "Business English",
    emoji = "📚",
    totalWords = 42,
    lastStudiedAt = System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000L,
)

private fun previewWords() = listOf(
    WordCard(
        id = "1", word = "acquire", translation = "приобретать", partOfSpeech = "verb",
        description = "To gain something through effort or experience.",
        exampleSentence = "She acquired new skills working abroad.",
        synonyms = listOf("obtain", "gain", "attain"), antonyms = listOf("lose", "forfeit"),
        collocations = listOf("acquire knowledge", "acquire skills", "acquire assets"),
        knowledgeLevel = KnowledgeLevel.LEARNING.name,
        isFavorite = true,
    ),
    WordCard(
        id = "2", collectionId = "preview", word = "Deadline", translation = "Дедлайн",
        knowledgeLevel = KnowledgeLevel.NEW.name
    ),
    WordCard(
        id = "3",
        collectionId = "preview",
        word = "Stakeholder",
        translation = "Заинтересованная сторона",
        knowledgeLevel = KnowledgeLevel.KNOWN.name
    ),
    WordCard(
        id = "4", collectionId = "preview", word = "Deadline", translation = "Дедлайн",
        knowledgeLevel = KnowledgeLevel.NEW.name
    ),
    WordCard(
        id = "5", collectionId = "preview", word = "Frost", translation = "Мороз",
        knowledgeLevel = KnowledgeLevel.KNOWN.name
    ),
    WordCard(
        id = "6", collectionId = "preview", word = "Deadline", translation = "Дедлайн",
        knowledgeLevel = KnowledgeLevel.REVIEWING.name
    ),
)

// ── Previews ──────────────────────────────────────────────────────────────────

@Preview(name = "Content — Loading")
@Composable
private fun CollectionDetailContentLoadingPreview() {
    MemorifyTheme {
        PreviewScaffold(
            topBarState = TopBarState(title = "Business English")
        ) {
            CollectionDetailContent(
                uiState = CollectionDetailUiState(isLoading = true),
                onAction = {},
                onSortClick = {},
            )
        }
    }
}

@Preview(name = "Content — With words")
@Composable
private fun CollectionDetailContentWithWordsPreview() {
    val words = previewWords()
    MemorifyTheme {
        PreviewScaffold(
            topBarState = TopBarState(
                content = { AppHeader(label = "Business English") }
            ),
            selectedNavItem = BottomNavItem.Home
        ) {
            CollectionDetailContent(
                uiState = CollectionDetailUiState(
                    isLoading = false,
                    collection = previewCollection(),
                    words = words,
                ),
                onAction = {},
                onSortClick = {},
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Preview(name = "Content — Empty, no filters")
@Composable
private fun CollectionDetailContentEmptyPreview() {
    MemorifyTheme {
        PreviewScaffold {
            CollectionDetailContent(
                uiState = CollectionDetailUiState(
                    isLoading = false,
                    collection = previewCollection(),
                    words = emptyList(),
                ),
                onAction = {},
                onSortClick = {},
            )
        }
    }
}

@Preview(name = "Content — Empty, filters active")
@Composable
private fun CollectionDetailContentEmptyFilteredPreview() {
    MemorifyTheme {
        PreviewScaffold {
            CollectionDetailContent(
                uiState = CollectionDetailUiState(
                    isLoading = false,
                    collection = previewCollection(),
                    words = emptyList(),
                    filterState = WordFilterState(
                        selectedLevels = setOf(KnowledgeLevel.LEARNING),
                        favoritesOnly = true,
                    ),
                ),
                onAction = {},
                onSortClick = {},
            )
        }
    }
}

@Preview(name = "Content — Error")
@Composable
private fun CollectionDetailContentErrorPreview() {
    MemorifyTheme {
        PreviewScaffold {
            CollectionDetailContent(
                uiState = CollectionDetailUiState(isError = true, isLoading = false),
                onAction = {},
                onSortClick = {},
            )
        }
    }
}

