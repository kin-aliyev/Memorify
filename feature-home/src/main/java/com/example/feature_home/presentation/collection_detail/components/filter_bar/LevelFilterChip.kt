package com.example.feature_home.presentation.collection_detail.components.filter_bar

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.MemorifyTheme

@Composable
fun LevelFilterChip(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    leadingIcon: ImageVector? = null
) {
    FilterChip(
        label = { Text(text = label, style = MaterialTheme.typography.labelMedium) },
        selected = selected,
        onClick = onClick,
        leadingIcon = leadingIcon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                )
            }
        },
        shape = CircleShape,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = MaterialTheme.colorScheme.surface,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            iconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = MaterialTheme.colorScheme.outlineVariant,
            selectedBorderColor = MaterialTheme.colorScheme.primary,
            selectedBorderWidth = 0.dp
        ),
        modifier = modifier,
    )
}

// ── Previews ──────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Inactive")
@Composable
private fun LevelFilterChipInactivePreview() {
    MemorifyTheme {
        LevelFilterChip(
            label = "Learning",
            selected = false,
            onClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Active")
@Composable
private fun LevelFilterChipActivePreview() {
    MemorifyTheme {
        LevelFilterChip(
            label = "Learning",
            selected = true,
            onClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Active with icon")
@Composable
private fun LevelFilterChipWithIconPreview() {
    MemorifyTheme {
        LevelFilterChip(
            label = "Favorites",
            selected = true,
            onClick = {},
            leadingIcon = Icons.Filled.Star,
        )
    }
}