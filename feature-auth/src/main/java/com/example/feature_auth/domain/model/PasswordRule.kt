package com.example.feature_auth.domain.model

enum class PasswordRule {
    MIN_LENGTH,
    HAS_UPPERCASE,
    HAS_LOWERCASE,
    HAS_DIGIT,
    HAS_SPECIAL_CHAR
}