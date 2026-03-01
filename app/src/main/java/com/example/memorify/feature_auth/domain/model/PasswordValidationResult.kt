package com.example.memorify.feature_auth.domain.model

data class PasswordValidationResult(
    val isValid: Boolean,
    val failedRules: List<PasswordRule> = emptyList()
)

