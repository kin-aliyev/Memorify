package com.example.feature_auth.presentation.model

import com.example.feature_auth.domain.model.PasswordRule

data class PasswordRuleUi(
    val rule: PasswordRule,
    val message: String,
    val satisfied: Boolean
)
