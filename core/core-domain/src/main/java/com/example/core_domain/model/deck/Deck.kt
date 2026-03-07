package com.example.core_domain.model.deck

data class Deck(
    val id: String = "",
    val name: String = "",
    val emoji: String = "📚",
    val color: String = DeckColor.ORANGE.name,
    val sourceLanguage: String = Language.RUSSIAN.code,
    val targetLanguage: String = Language.ENGLISH.code,
    val wordCount: Int = 0,
    val lastStudiedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)