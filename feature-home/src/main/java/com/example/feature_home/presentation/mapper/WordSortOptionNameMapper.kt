package com.example.feature_home.presentation.mapper

import androidx.compose.runtime.Composable
import com.example.feature_home.presentation.collection_detail.model.WordSortOption

@Composable
fun WordSortOption.displayLabel(): String = when (this) {
    WordSortOption.NEWEST_FIRST -> "Newest"
    WordSortOption.OLDEST_FIRST -> "Oldest"
    WordSortOption.ALPHA_ASC -> "A → Z"
    WordSortOption.ALPHA_DESC -> "Z → A"
    WordSortOption.KNOWLEDGE_NEW_FIRST -> "New first"
    WordSortOption.KNOWLEDGE_KNOWN_FIRST -> "Known first"
}