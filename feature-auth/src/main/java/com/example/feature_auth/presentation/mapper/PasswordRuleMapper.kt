package com.example.feature_auth.presentation.mapper

import com.example.feature_auth.domain.provider.AuthStringProvider
import com.example.feature_auth.domain.model.PasswordRule
import com.example.feature_auth.presentation.model.PasswordRuleUi

fun mapPasswordRules(
    failedRules: List<PasswordRule>,
    stringProvider: AuthStringProvider,
): List<PasswordRuleUi> {
    return PasswordRule.entries.map { rule ->
        PasswordRuleUi(
            rule = rule,
            message = stringProvider.getPasswordRuleMessage(rule),
            satisfied = !failedRules.contains(rule)
        )
    }
}