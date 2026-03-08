package com.example.core_data.mapper

import com.example.core_domain.model.deck.Deck
import com.example.core_domain.model.deck.DeckColor
import com.example.core_domain.model.deck.Language
import com.google.firebase.firestore.DocumentSnapshot

fun Deck.toMap(): Map<String, Any?> = mapOf(
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

fun DocumentSnapshot.toDeck(): Deck? = try {
    Deck(
        id = getString("id") ?: return null,
        name = getString("name") ?: "",
        emoji = getString("emoji") ?: "📚",
        color = getString("color") ?: DeckColor.ORANGE.name,
        sourceLanguage = getString("sourceLanguage") ?: Language.RUSSIAN.code,
        targetLanguage = getString("targetLanguage") ?: Language.ENGLISH.code,
        wordCount = getLong("wordCount")?.toInt() ?: 0,
        lastStudiedAt = getLong("lastStudiedAt"),
        createdAt = getLong("createdAt") ?: System.currentTimeMillis()
    )
} catch (e: Exception) { null }