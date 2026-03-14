package com.example.feature_home.presentation.model

import com.example.core_domain.model.deck.Collection

data class CollectionUiModel(
    val id: String,
    val name: String,
    val emoji: String,
    val totalWords: Int,
    val reviewedWords: Int,
    val lastStudiedAt: Long?
)

fun Collection.toUiModel() = CollectionUiModel(
    id = id,
    name = name,
    emoji = emoji,
    totalWords = wordCount,
    reviewedWords = 0,
    lastStudiedAt = lastStudiedAt,
)
