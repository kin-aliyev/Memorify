package com.example.core_domain.model.word

enum class KnowledgeLevel {
    NEW,       // ни разу не повторял
    LEARNING,  // intervalDays < 7
    REVIEWING, // intervalDays 7-21
    MASTERED;  // intervalDays > 21

    companion object {
        fun fromString(value: String): KnowledgeLevel =
            runCatching { valueOf(value) }.getOrDefault(NEW)
    }
}