package com.example.feature_home.presentation.collection_detail.components.filter_bar

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.MemorifyTheme
import com.example.feature_home.presentation.collection_detail.model.WordSortOption
import com.example.feature_home.presentation.mapper.displayIcon
import com.example.feature_home.presentation.mapper.displayLabel

@Composable
fun SortWordsChip(
    modifier: Modifier = Modifier,
    sortOption: WordSortOption,
    onClick: () -> Unit,
) {
    FilterChip(
        selected = true,
        onClick = onClick,
        label = {
            Text(
                text = sortOption.displayLabel(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = sortOption.displayIcon(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowDown,
                contentDescription = "Open sort options",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        shape = CircleShape,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = false,
            borderColor = MaterialTheme.colorScheme.outlineVariant,
            selectedBorderWidth = 0.dp,
        ),
        modifier = modifier,
    )
}

// ── Previews ──────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Newest")
@Composable
private fun SortWordsChipNewestPreview() {
    MemorifyTheme {
        SortWordsChip(sortOption = WordSortOption.NEWEST_FIRST, onClick = {})
    }
}

@Preview(showBackground = true, name = "Alpha")
@Composable
private fun SortWordsChipAlphaPreview() {
    MemorifyTheme {
        SortWordsChip(sortOption = WordSortOption.ALPHA_ASC, onClick = {})
    }
}