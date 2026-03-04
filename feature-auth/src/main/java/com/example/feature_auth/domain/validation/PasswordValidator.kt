package com.example.feature_auth.domain.validation

import com.example.feature_auth.domain.model.PasswordValidationResult

interface PasswordValidator {
    fun validate(password: String): PasswordValidationResult
}