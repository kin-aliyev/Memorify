package com.example.feature_auth.data.validation

import com.example.feature_auth.domain.model.PasswordValidationResult
import com.example.feature_auth.domain.model.PasswordRule
import com.example.feature_auth.domain.validation.PasswordValidator
import javax.inject.Inject

class PasswordValidatorImpl @Inject constructor() : PasswordValidator {

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
        private val SPECIAL_CHARS_REGEX = Regex("[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]")
    }

    override fun validate(password: String): PasswordValidationResult {
        val failedRules = mutableListOf<PasswordRule>()

        if (password.length < MIN_PASSWORD_LENGTH) failedRules.add(PasswordRule.MIN_LENGTH)
        if (!password.any { it.isUpperCase() }) failedRules.add(PasswordRule.HAS_UPPERCASE)
        if (!password.any { it.isLowerCase() }) failedRules.add(PasswordRule.HAS_LOWERCASE)
        if (!password.any { it.isDigit() }) failedRules.add(PasswordRule.HAS_DIGIT)

        if (!SPECIAL_CHARS_REGEX.containsMatchIn(password)) {
            failedRules.add(PasswordRule.HAS_SPECIAL_CHAR)
        }

        return PasswordValidationResult(
            isValid = failedRules.isEmpty(),
            failedRules = failedRules
        )
    }
}