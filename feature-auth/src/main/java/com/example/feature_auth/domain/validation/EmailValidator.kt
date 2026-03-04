package com.example.feature_auth.domain.validation

interface EmailValidator {
    fun isValid(email: String): Boolean
}