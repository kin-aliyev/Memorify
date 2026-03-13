package com.example.core_ui.utils

import android.content.Context
import com.example.core_ui.R
import java.time.ZoneId
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun formatLastUsed(
    context: Context,
    timestampMillis: Long,
): String {
    val zone = ZoneId.systemDefault()
    val date = Instant.ofEpochMilli(timestampMillis).atZone(zone).toLocalDate()
    val today = LocalDate.now(zone)
    val daysAgo = ChronoUnit.DAYS.between(date, today)

    return when {
        daysAgo == 0L -> context.getString(R.string.last_used_today)
        daysAgo == 1L -> context.getString(R.string.last_used_yesterday)
        daysAgo < 7L -> context.getString(R.string.last_used_days_ago, daysAgo)
        daysAgo < 30L -> {
            val weeksAgo = daysAgo / 7
            context.getString(R.string.last_used_weeks_ago, weeksAgo)
        }

        else -> {
            val formatted = DateTimeFormatter.ofPattern("dd.MM.yyyy").format(date)
            context.getString(R.string.last_used_date, formatted)
        }
    }
}