package com.example.core_domain.model.collection

data class Collection(
    val id: String = "",
    val name: String = "",
    val emoji: String = "📚",
    val color: String = CollectionColor.ORANGE.name,
    val sourceLanguage: String = Language.RUSSIAN.code,
    val targetLanguage: String = Language.ENGLISH.code,
    val wordCount: Int = 0,
    val lastStudiedAt: Long? = null,
    val createdAt: Long = System.currentTimeMillis()
)