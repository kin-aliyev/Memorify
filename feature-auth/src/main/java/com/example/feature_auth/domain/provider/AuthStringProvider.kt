package com.example.feature_auth.domain.provider

import com.example.core_domain.exception.AppException
import com.example.feature_auth.domain.model.PasswordRule

interface AuthStringProvider {
    fun getErrorMessage(exception: AppException): String
    fun getPasswordRuleMessage(rule: PasswordRule): String
}