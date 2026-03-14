package com.example.core_domain.model.word

data class WordCard(
    // Identification
    val id: String = "",
    val collectionId: String = "",

    // Content
    val word: String = "",
    val translation: String = "",
    val description: String = "",
    val partOfSpeech: String = "",
    val exampleSentence: String = "",
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList(),
    val collocations: List<String> = emptyList(),

    // SRS
    val srs: SrsData = SrsData(),
    val knowledgeLevel: String = KnowledgeLevel.NEW.name,

    // Statistics
    val reviewCount: Int = 0,
    val correctCount: Int = 0,

    // Metadata
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
