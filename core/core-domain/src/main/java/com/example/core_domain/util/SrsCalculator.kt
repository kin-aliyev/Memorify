package com.example.core_domain.util

import com.example.core_domain.model.word.SrsData

object SrsCalculator {
    // ── Настройки ──────────────────────────────────────────
    private const val MIN_EASE_FACTOR      = 1.3
    private const val MAX_EASE_FACTOR      = 3.0
    private const val MAX_INTERVAL_DAYS    = 365

    // Первые два интервала основаны на исследованиях Вознака
    private const val FIRST_INTERVAL       = 1
    private const val SECOND_INTERVAL      = 6

    // Мультипликаторы для "Не уверен"
    private const val HARD_INTERVAL_MULT   = 1.2
    private const val HARD_EASE_PENALTY    = 0.2


    /**
     * 0 = Не знаю   → провал
     * 1 = Не уверен → с трудом но правильно
     * 2 = Знаю      → уверенный ответ
     *
     * Маппим в SM-2 шкалу 0-5:
     * 0 → q=1 (incorrect, answer felt familiar)
     * 1 → q=3 (correct with serious difficulty)
     * 2 → q=5 (perfect response)
     */
    private fun toSM2Grade(rating: Int): Int = when (rating) {
        0 -> 1
        1 -> 3
        2 -> 5
        else -> throw IllegalArgumentException("Rating must be 0, 1 or 2")
    }

    fun calculate(current: SrsData, rating: Int): SrsData {
        require(rating in 0..2) { "Rating must be 0, 1 or 2" }

        val now = System.currentTimeMillis()
        val q = toSM2Grade(rating)

        val newEaseFactor = (current.easeFactor + (0.1 - (5 - q) * (0.08 + (5 - q) * 0.02)))
            .coerceIn(MIN_EASE_FACTOR, MAX_EASE_FACTOR)

        return when {
            // ── Не знаю (q < 3) ────────────────────────────
            q < 3 -> current.copy(
                lastReviewedAt = now,
                repetitions  = 0,
                intervalDays = FIRST_INTERVAL,
                nextReviewAt   = daysFromNow(FIRST_INTERVAL),
                easeFactor   = newEaseFactor
            )

            // ── Не уверен (q = 3) ──────────────────────────
            // Интервал растёт медленно через HARD_INTERVAL_MULT
            q == 3 -> {
                val newInterval = when(current.repetitions) {
                    0 -> FIRST_INTERVAL
                    else -> (current.intervalDays * HARD_INTERVAL_MULT).toInt()
                        .coerceAtLeast(current.intervalDays + 1)
                        .coerceAtMost(MAX_INTERVAL_DAYS)
                }
                current.copy(
                    lastReviewedAt = now,
                    repetitions = current.repetitions + 1,
                    intervalDays = newInterval,
                    nextReviewAt = daysFromNow(newInterval),
                    easeFactor   = maxOf(MIN_EASE_FACTOR, current.easeFactor - HARD_EASE_PENALTY)
                )
            }

            // ── Знаю (q = 5) ───────────────────────────────
            else -> {
                val newRepetitions = current.repetitions + 1
                val rawInterval = when(newRepetitions) {
                    1 -> FIRST_INTERVAL
                    2 -> SECOND_INTERVAL
                    else -> (current.intervalDays * newEaseFactor).toInt()
                }
                val newInterval = rawInterval
                    .coerceAtLeast(current.intervalDays + 1)
                    .coerceAtMost(MAX_INTERVAL_DAYS)

                current.copy(
                    lastReviewedAt = now,
                    repetitions = newRepetitions,
                    intervalDays = newInterval,
                    nextReviewAt = daysFromNow(newInterval),
                    easeFactor = newEaseFactor
                )
            }
        }
    }

    private fun daysFromNow(days: Int): Long =
        System.currentTimeMillis() + days * 24L * 60 * 60 * 1000
}