package com.example.memorify.feature_auth.domain.provider

import com.example.memorify.feature_auth.domain.exception.AuthException
import com.example.memorify.feature_auth.domain.model.PasswordRule

interface AuthStringProvider {
    fun getErrorMessage(exception: AuthException): String
    fun getPasswordRuleMessage(rule: PasswordRule): String
}