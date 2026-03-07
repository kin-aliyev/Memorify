package com.example.core_domain.model.word

data class WordCard(
    // Identification
    val id: String = "",
    val deckId: String = "",

    // Content
    val word: String = "",
    val translation: String = "",
    val description: String = "",
    val partOfSpeech: String = "",
    val exampleSentence: String = "",
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList(),
    val collocations: List<String> = emptyList(),

    // Metadata
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
