package com.example.core_data.mapper

import com.example.core_domain.model.collection.Collection
import com.example.core_domain.model.collection.CollectionColor
import com.example.core_domain.model.collection.Language
import com.google.firebase.firestore.DocumentSnapshot

fun Collection.toMap(): Map<String, Any?> = mapOf(
    "id" to id,
    "name" to name,
    "emoji" to emoji,
    "color" to color,
    "sourceLanguage" to sourceLanguage,
    "targetLanguage" to targetLanguage,
    "wordCount" to wordCount,
    "lastStudiedAt" to lastStudiedAt,
    "createdAt" to createdAt
)

fun DocumentSnapshot.toDeck(): Collection? = try {
    Collection(
        id = getString("id") ?: return null,
        name = getString("name") ?: "",
        emoji = getString("emoji") ?: "📚",
        color = getString("color") ?: CollectionColor.ORANGE.name,
        sourceLanguage = getString("sourceLanguage") ?: Language.RUSSIAN.code,
        targetLanguage = getString("targetLanguage") ?: Language.ENGLISH.code,
        wordCount = getLong("wordCount")?.toInt() ?: 0,
        lastStudiedAt = getLong("lastStudiedAt"),
        createdAt = getLong("createdAt") ?: System.currentTimeMillis()
    )
} catch (e: Exception) { null }