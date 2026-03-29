package com.example.feature_home.presentation.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.SortByAlpha
import androidx.compose.material.icons.outlined.TextRotationDown
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.feature_home.presentation.collection_detail.model.WordSortOption

fun WordSortOption.displayIcon(): ImageVector = when (this) {
    WordSortOption.NEWEST_FIRST          -> Icons.Outlined.Schedule
    WordSortOption.OLDEST_FIRST          -> Icons.Outlined.History
    WordSortOption.ALPHA_ASC             -> Icons.Outlined.SortByAlpha
    WordSortOption.ALPHA_DESC            -> Icons.Outlined.TextRotationDown
    WordSortOption.KNOWLEDGE_NEW_FIRST   -> Icons.Outlined.AutoStories
    WordSortOption.KNOWLEDGE_KNOWN_FIRST -> Icons.Outlined.EmojiEvents
}

