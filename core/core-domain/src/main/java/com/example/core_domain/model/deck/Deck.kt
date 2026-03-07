package com.example.core_domain.model.deck

data class Deck(
    val id: String = "",
    val name: String = "",
    val emoji: String = "📚",
    val color: String = "ORANGE",
    val sourceLanguage: String = "ru",
    val targetLanguage: String = "en",
    val wordCount: Int = 0,
    val lastStudiedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)