package com.example.feature_home.presentation.collections

import androidx.compose.foundation.layout.Arrangement
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
import com.example.core_domain.model.collection.CollectionColor
import com.example.core_ui.Dimens
import com.example.core_ui.common.LoadingOverlay
import com.example.core_ui.common.scaffold.AppHeader
import com.example.core_ui.common.scaffold.PreviewScaffold
import com.example.core_ui.model.BottomNavItem
import com.example.core_ui.model.TopBarState
import com.example.core_ui.theme.MemorifyTheme
import com.example.feature_home.presentation.collections.components.CollectionItem
import com.example.feature_home.presentation.common.SpeedDialFab
import com.example.feature_home.presentation.common.SpeedDialItem
import com.example.feature_home.presentation.model.CollectionUiModel

@Composable
fun CollectionsScreenContent(
    modifier: Modifier = Modifier,
    uiState: CollectionsUiState,
    onAction: (CollectionsAction) -> Unit,
) {
    when {
        uiState.isLoading -> {
            LoadingOverlay(isLoading = uiState.isLoading)
        }

        uiState.collections.isEmpty() -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
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
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacing12),
                contentPadding = PaddingValues(
                    horizontal = Dimens.paddingScreen,
                    vertical = Dimens.spacing16,
                )
            ) {
                item {
                    Text(
                        text = "Your collections",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(bottom = Dimens.spacing8),
                    )
                }

                items(
                    items = uiState.collections,
                    key = { deck -> deck.id }
                ) { collection ->
                    CollectionItem(
                        label = collection.name,
                        emoji = collection.emoji,
                        color = collection.color,
                        reviewedWords = collection.reviewedWords,
                        totalWords = collection.totalWords,
                        lastUsedDate = collection.lastStudiedAt ?: 0L,
                        onClick = { onAction(CollectionsAction.OnCollectionClick(collection.id)) }
                    )
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
                uiState = CollectionsUiState(collections = emptyList(), isLoading = false),
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
                    collections = listOf(
                        CollectionUiModel(
                            id = "1",
                            name = "English Basics",
                            emoji = "📚",
                            color = CollectionColor.ORANGE,
                            totalWords = 100,
                            reviewedWords = 45,
                            lastStudiedAt = System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000L,
                        ),
                        CollectionUiModel(
                            id = "2",
                            name = "Spanish Travel",
                            emoji = "✈️",
                            color = CollectionColor.ORANGE,
                            totalWords = 50,
                            reviewedWords = 50,
                            lastStudiedAt = System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000L,
                        ),
                        CollectionUiModel(
                            id = "3",
                            name = "Tech Terms",
                            emoji = "💻",
                            color = CollectionColor.ORANGE,
                            totalWords = 200,
                            reviewedWords = 0,
                            lastStudiedAt = null,
                        ),
                        CollectionUiModel(
                            id = "11",
                            name = "Russian Basics",
                            emoji = "📚",
                            color = CollectionColor.ORANGE,
                            totalWords = 125,
                            reviewedWords = 45,
                            lastStudiedAt = System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000L,
                        ),
                        CollectionUiModel(
                            id = "21",
                            name = "China Travel",
                            emoji = "✈️",
                            color = CollectionColor.ORANGE,
                            totalWords = 150,
                            reviewedWords = 50,
                            lastStudiedAt = System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000L,
                        ),
                        CollectionUiModel(
                            id = "32",
                            name = "Backend Terms",
                            emoji = "💻",
                            color = CollectionColor.ORANGE,
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