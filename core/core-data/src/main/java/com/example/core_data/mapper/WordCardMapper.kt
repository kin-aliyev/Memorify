package com.example.core_data.mapper

import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_domain.model.word.SrsData
import com.example.core_domain.model.word.WordCard
import com.google.firebase.firestore.DocumentSnapshot

fun WordCard.toMap(): Map<String, Any?> = mapOf(
    "id" to id,
    "collectionId" to collectionId,
    "word" to word,
    "translation" to translation,
    "description" to description,
    "partOfSpeech" to partOfSpeech,
    "exampleSentence" to exampleSentence,
    "synonyms" to synonyms,
    "antonyms" to antonyms,
    "collocations" to collocations,
    "srs" to mapOf(
        "easeFactor" to srs.easeFactor,
        "intervalDays"   to srs.intervalDays,
        "repetitions" to srs.repetitions,
        "nextReviewAt" to srs.nextReviewAt,
        "lastReviewedAt" to srs.lastReviewedAt
    ),
    "knowledgeLevel" to knowledgeLevel,
    "reviewCount" to reviewCount,
    "correctCount" to correctCount,
    "isFavorite" to isFavorite,
    "createdAt" to createdAt
)

@Suppress("UNCHECKED_CAST")
fun DocumentSnapshot.toWordCard(): WordCard? = try {
    val srsMap = get("srs") as? Map<String, Any> ?: emptyMap()
    WordCard(
        id = getString("id") ?: return null,
        collectionId = getString("collectionId") ?: return null,
        word = getString("word") ?: "",
        translation = getString("translation") ?: "",
        description = getString("description") ?: "",
        partOfSpeech = getString("partOfSpeech") ?: "",
        exampleSentence = getString("exampleSentence") ?: "",
        synonyms = get("synonyms") as? List<String> ?: emptyList(),
        antonyms = get("antonyms") as? List<String> ?: emptyList(),
        collocations = get("collocations") as? List<String> ?: emptyList(),
        srs = SrsData(
            easeFactor = (srsMap["easeFactor"] as? Double) ?: 2.5,
            intervalDays = (srsMap["intervalDays"] as? Long)?.toInt() ?: 0,
            repetitions = (srsMap["repetitions"] as? Long)?.toInt() ?: 0,
            nextReviewAt = (srsMap["nextReviewAt"] as? Long)
                ?: System.currentTimeMillis(),
            lastReviewedAt = srsMap["lastReviewedAt"] as? Long
        ),
        knowledgeLevel = getString("knowledgeLevel") ?: KnowledgeLevel.NEW.name,
        reviewCount = getLong("reviewCount")?.toInt() ?: 0,
        correctCount = getLong("correctCount")?.toInt() ?: 0,
        isFavorite = getBoolean("isFavorite") ?: false,
        createdAt = getLong("createdAt") ?: System.currentTimeMillis()
    )
} catch(e: Exception) { null }