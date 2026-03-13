package com.example.feature_auth.presentation.mapper

import android.content.Context
import com.example.memorify.feature_auth.R
import com.example.core_domain.exception.AppException
import com.example.feature_auth.domain.provider.AuthStringProvider
import com.example.feature_auth.domain.model.PasswordRule
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceAuthStringProvider @Inject constructor(
    @param:ApplicationContext private val context: Context
) : AuthStringProvider {
    override fun getErrorMessage(exception: AppException): String = when (exception) {
        // Validation errors
        AppException.EmptyEmail -> context.getString(R.string.error_empty_email)
        AppException.EmptyPassword -> context.getString(R.string.error_empty_password)
        AppException.InvalidEmail -> context.getString(R.string.error_invalid_email)
        AppException.WeakPassword -> context.getString(R.string.error_weak_password)
        AppException.PasswordMismatch -> context.getString(R.string.error_passwords_do_not_match)

        // Auth errors
        AppException.InvalidCredentials -> context.getString(R.string.error_invalid_credentials)
        AppException.UserNotFound -> context.getString(R.string.error_user_not_found)
        AppException.UserAlreadyExists -> context.getString(R.string.error_user_already_exists)
        AppException.NetworkError -> context.getString(R.string.error_network_connection)
        AppException.ReAuthRequired -> context.getString(R.string.error_reauth_required)

        // Google errors
        AppException.GoogleAccountNotFound -> context.getString(R.string.error_google_account_not_found)
        AppException.GoogleAuthCancelled -> context.getString(R.string.error_google_auth_cancelled)
        AppException.GoogleAuthFailed -> context.getString(R.string.error_google_auth_failed)

        is AppException.UnknownError -> {
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