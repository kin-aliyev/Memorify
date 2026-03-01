package com.example.memorify.feature_auth.presentation.model

import com.example.memorify.feature_auth.domain.model.PasswordRule

data class PasswordRuleUi(
    val rule: PasswordRule,
    val message: String,
    val satisfied: Boolean
)
