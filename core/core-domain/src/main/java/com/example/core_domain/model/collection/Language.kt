package com.example.core_domain.model.collection

enum class Language(
    val code: String,
    val displayName: String,
    val flag: String
) {
    ENGLISH("en", "English", "🇬🇧"),
    RUSSIAN("ru", "Русский", "🇷🇺"),
    AZERBAIJANI("az", "Azərbaycan", "🇦🇿"),
    GERMAN("de", "Deutsch", "🇩🇪"),
    FRENCH("fr", "Français", "🇫🇷"),
    SPANISH("es", "Español", "🇪🇸"),
    ITALIAN("it", "Italiano", "🇮🇹");

    companion object {
        fun fromCode(code: String): Language =
            entries.find { it.code == code } ?: ENGLISH
    }
}