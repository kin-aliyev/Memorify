package com.example.memorify.feature_auth.presentation.mapper

import android.content.Context
import com.example.memorify.R
import com.example.memorify.feature_auth.domain.exception.AuthException
import com.example.memorify.feature_auth.domain.provider.AuthStringProvider
import com.example.memorify.feature_auth.domain.model.PasswordRule
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceAuthStringProvider @Inject constructor(
    @param:ApplicationContext private val context: Context
) : AuthStringProvider {
    override fun getErrorMessage(exception: AuthException): String = when (exception) {
        // Validation errors
        AuthException.EmptyEmail -> context.getString(R.string.error_empty_email)
        AuthException.EmptyPassword -> context.getString(R.string.error_empty_password)
        AuthException.InvalidEmail -> context.getString(R.string.error_invalid_email)
        AuthException.WeakPassword -> context.getString(R.string.error_weak_password)
        AuthException.PasswordMismatch -> context.getString(R.string.error_passwords_do_not_match)

        // Auth errors
        AuthException.InvalidCredentials -> context.getString(R.string.error_invalid_credentials)
        AuthException.UserNotFound -> context.getString(R.string.error_user_not_found)
        AuthException.UserAlreadyExists -> context.getString(R.string.error_user_already_exists)
        AuthException.NetworkError -> context.getString(R.string.error_network_connection)
        AuthException.ReAuthRequired -> context.getString(R.string.error_reauth_required)

        // Google errors
        AuthException.GoogleAccountNotFound -> context.getString(R.string.error_google_account_not_found)
        AuthException.GoogleAuthCancelled -> context.getString(R.string.error_google_auth_cancelled)
        AuthException.GoogleAuthFailed -> context.getString(R.string.error_google_auth_failed)

        is AuthException.UnknownError -> {
            val baseMessage = context.getString(R.string.error_unknown)
            exception.errorMessage?.let { message -> "$baseMessage: $message" } ?: baseMessage
        }
    }

    override fun getPasswordRuleMessage(rule: PasswordRule): String = when (rule) {
        PasswordRule.MIN_LENGTH -> context.getString(R.string.password_rule_min_length)
        PasswordRule.HAS_UPPERCASE -> context.getString(R.string.password_rule_uppercase)
        PasswordRule.HAS_LOWERCASE -> context.getString(R.string.password_rule_lowercase)
        PasswordRule.HAS_DIGIT -> context.getString(R.string.password_rule_digit)
        PasswordRule.HAS_SPECIAL_CHAR -> context.getString(R.string.password_rule_special_char)
    }
}