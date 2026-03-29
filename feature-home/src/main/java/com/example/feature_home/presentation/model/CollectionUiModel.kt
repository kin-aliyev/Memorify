package com.example.feature_home.presentation.model

import com.example.core_domain.model.collection.Collection
import com.example.core_domain.model.collection.CollectionColor

data class CollectionUiModel(
    val id: String,
    val name: String,
    val emoji: String,
    val color: CollectionColor,
    val totalWords: Int,
    val reviewedWords: Int,
    val lastStudiedAt: Long?
)

fun Collection.toUiModel() = CollectionUiModel(
    id = id,
    name = name,
    emoji = emoji,
    color = CollectionColor.valueOf(color),
    totalWords = totalWords,
    reviewedWords = 0,
    lastStudiedAt = lastStudiedAt,
)
