package com.example.core_domain.model.word

data class SrsData(
    val easeFactor: Double = 2.5,   // множитель интервала (сложность карточки)
    val intervalDays: Int = 0,      // через сколько дней показать
    val repetitions: Int = 0,       // подряд правильных ответов
    val nextReviewAt: Long = System.currentTimeMillis(),
    val lastReviewedAt: Long? = null
) {
    fun toKnowledgeLevel(): KnowledgeLevel = when {
        lastReviewedAt == null  -> KnowledgeLevel.NEW
        intervalDays >= 21      -> KnowledgeLevel.MASTERED
        intervalDays >= 7       -> KnowledgeLevel.REVIEWING
        else                    -> KnowledgeLevel.LEARNING
    }
}