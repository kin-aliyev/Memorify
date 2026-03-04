package com.example.feature_auth.data.validation

import android.util.Patterns
import com.example.feature_auth.domain.validation.EmailValidator
import javax.inject.Inject

class EmailValidatorImpl @Inject constructor(): EmailValidator {
    override fun isValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}