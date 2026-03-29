package com.example.feature_home.presentation.collection_detail.components.filter_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_ui.Dimens
import com.example.core_ui.mapper.displayName
import com.example.core_ui.theme.MemorifyTheme
import com.example.feature_home.presentation.collection_detail.model.WordFilterState
import com.example.feature_home.presentation.collection_detail.model.WordSortOption

@Composable
fun FilterBar(
    modifier: Modifier = Modifier,
    filterState: WordFilterState,
    onClearFilters: () -> Unit,
    onLevelToggle: (KnowledgeLevel) -> Unit,
    onFavoritesToggle: () -> Unit,
    onSortClick: () -> Unit
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        item {
            WordFilterChip(
                label = "All",
                selected = filterState.selectedLevels.isEmpty() && !filterState.favoritesOnly,
                onClick = onClearFilters,
            )
        }

        items(KnowledgeLevel.entries) { level ->
            WordFilterChip(
                label = level.displayName(),
                selected = level in filterState.selectedLevels,
                onClick = { onLevelToggle(level) },
            )
        }

        item {
            WordFilterChip(
                label = "Favorites",
                selected = filterState.favoritesOnly,
                onClick = onFavoritesToggle,
                leadingIcon = Icons.Default.Star
            )
        }

        item {
            VerticalDivider(
                modifier = Modifier.height(Dimens.heightChip),
                color = MaterialTheme.colorScheme.outlineVariant,
            )
        }

        item {
            SortWordsChip(
                sortOption = filterState.sortOption,
                onClick = onSortClick,
            )
        }
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "No filters active",)
@Composable
private fun FilterBarDefaultPreview() {
    MemorifyTheme {
        FilterBar(
            filterState = WordFilterState(),
            onLevelToggle = {},
            onFavoritesToggle = {},
            onSortClick = {},
            onClearFilters = {}
        )
    }
}

@Preview(showBackground = true, name = "Learning + Favorites active")
@Composable
private fun FilterBarActivePreview() {
    MemorifyTheme {
        FilterBar(
            filterState = WordFilterState(
                selectedLevels = setOf(KnowledgeLevel.LEARNING),
                favoritesOnly = true,
                sortOption = WordSortOption.ALPHA_ASC,
            ),
            onLevelToggle = {},
            onFavoritesToggle = {},
            onSortClick = {},
            onClearFilters = {}
        )
    }
}