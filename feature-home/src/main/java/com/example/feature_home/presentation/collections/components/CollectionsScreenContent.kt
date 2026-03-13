package com.example.feature_home.presentation.collections.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.Dimens
import com.example.core_ui.common.LoadingOverlay
import com.example.core_ui.common.scaffold.AppHeader
import com.example.core_ui.common.scaffold.PreviewScaffold
import com.example.core_ui.model.BottomNavItem
import com.example.core_ui.model.TopBarState
import com.example.core_ui.theme.MemorifyTheme
import com.example.feature_home.presentation.collections.CollectionsAction
import com.example.feature_home.presentation.collections.CollectionsUiState
import com.example.feature_home.presentation.common.SpeedDialFab
import com.example.feature_home.presentation.common.SpeedDialItem
import com.example.feature_home.presentation.model.DeckUiModel

@Composable
fun CollectionsScreenContent(
    modifier: Modifier = Modifier,
    uiState: CollectionsUiState,
    onAction: (CollectionsAction) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                LoadingOverlay(isLoading = uiState.isLoading)
            }

            uiState.decks.isEmpty() -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.CreateNewFolder,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.4f),
                        modifier = Modifier
                            .size(Dimens.iconXxl)
                    )

                    Text(
                        text = "No collections\nClick „+“ to add one",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.4f),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(Dimens.spacing12),
                    contentPadding = PaddingValues(
                        horizontal = Dimens.paddingScreen,
                        vertical = Dimens.spacing16,
                    )
                ) {
                    items(
                        items = uiState.decks,
                        key = { deck -> deck.id }
                    ) { deck ->
                        CollectionItem(
                            label = deck.name,
                            reviewedWords = deck.reviewedWords,
                            totalWords = deck.totalWords,
                            lastUsedDate = deck.lastStudiedAt ?: 0L,
                            onClick = { onAction(CollectionsAction.OnCollectionClick(deck.id)) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Collections — Loading")
@Composable
private fun CollectionsLoadingPreview() {
    MemorifyTheme {
        PreviewScaffold(
            topBarState = TopBarState(content = { AppHeader(label = "Memorify") }),
            selectedNavItem = BottomNavItem.Home,
        ) { innerPadding ->
            CollectionsScreenContent(
                uiState = CollectionsUiState(isLoading = true),
                onAction = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Preview(name = "Collections — Empty")
@Composable
private fun CollectionsEmptyPreview() {
    MemorifyTheme {
        PreviewScaffold(
            topBarState = TopBarState(content = { AppHeader(label = "Memorify") }),
            selectedNavItem = BottomNavItem.Home,
        ) { innerPadding ->
            CollectionsScreenContent(
                uiState = CollectionsUiState(decks = emptyList(), isLoading = false),
                onAction = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Preview(name = "Collections — With Data")
@Composable
private fun CollectionsWithDataPreview() {
    MemorifyTheme {
        PreviewScaffold(
            topBarState = TopBarState(
                content = { AppHeader(label = "Memorify") },
                fab = {
                    var expanded by remember { mutableStateOf(true) }
                    SpeedDialFab(
                        expanded = expanded,
                        onToggle = { expanded = !expanded },
                        items = listOf(
                            SpeedDialItem(
                                icon = Icons.Default.CreateNewFolder,
                                label = "New collection",
                                onClick = {},
                            ),
                            SpeedDialItem(
                                icon = Icons.Default.Edit,
                                label = "Add word manually",
                                onClick = {},
                            ),
                            SpeedDialItem(
                                icon = Icons.Default.AutoAwesome,
                                label = "Generate with AI",
                                onClick = {},
                            ),
                        ),
                    )
                },
            ),
            selectedNavItem = BottomNavItem.Home,
        ) { innerPadding ->
            CollectionsScreenContent(
                uiState = CollectionsUiState(
                    isLoading = false,
                    decks = listOf(
                        DeckUiModel(
                            id = "1",
                            name = "English Basics",
                            emoji = "📚",
                            totalWords = 100,
                            reviewedWords = 45,
                            lastStudiedAt = System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000L,
                        ),
                        DeckUiModel(
                            id = "2",
                            name = "Spanish Travel",
                            emoji = "✈️",
                            totalWords = 50,
                            reviewedWords = 50,
                            lastStudiedAt = System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000L,
                        ),
                        DeckUiModel(
                            id = "3",
                            name = "Tech Terms",
                            emoji = "💻",
                            totalWords = 200,
                            reviewedWords = 0,
                            lastStudiedAt = null,
                        ),
                        DeckUiModel(
                            id = "11",
                            name = "Russian Basics",
                            emoji = "📚",
                            totalWords = 125,
                            reviewedWords = 45,
                            lastStudiedAt = System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000L,
                        ),
                        DeckUiModel(
                            id = "21",
                            name = "China Travel",
                            emoji = "✈️",
                            totalWords = 150,
                            reviewedWords = 50,
                            lastStudiedAt = System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000L,
                        ),
                        DeckUiModel(
                            id = "32",
                            name = "Backend Terms",
                            emoji = "💻",
                            totalWords = 200,
                            reviewedWords = 185,
                            lastStudiedAt = null,
                        ),
//                        DeckUiModel(
//                            id = "12",
//                            name = "English Basics",
//                            emoji = "📚",
//                            totalWords = 100,
//                            reviewedWords = 45,
//                            lastStudiedAt = System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000L,
//                        ),
//                        DeckUiModel(
//                            id = "22",
//                            name = "Spanish Travel",
//                            emoji = "✈️",
//                            totalWords = 50,
//                            reviewedWords = 50,
//                            lastStudiedAt = System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000L,
//                        ),
//                        DeckUiModel(
//                            id = "31",
//                            name = "Tech Terms",
//                            emoji = "💻",
//                            totalWords = 200,
//                            reviewedWords = 0,
//                            lastStudiedAt = null,
//                        ),
                    )
                ),
                onAction = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}