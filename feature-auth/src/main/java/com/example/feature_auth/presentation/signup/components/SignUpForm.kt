package com.example.feature_auth.presentation.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.memorify.feature_auth.R
import com.example.core_ui.components.Dimens
import com.example.core_ui.components.Dimens.elevation2
import com.example.core_ui.components.PrimaryButton
import com.example.core_ui.theme.MemorifyTheme
import com.example.feature_auth.presentation.signup.SignUpAction
import com.example.feature_auth.presentation.signup.SignUpUiState
import com.example.feature_auth.presentation.components.EmailField
import com.example.feature_auth.presentation.components.PasswordField

@Composable
fun SignUpForm(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onAction: (SignUpAction) -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(elevation2),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing12),
            modifier = Modifier.padding(Dimens.paddingMd)
        ) {
            EmailField(
                email = uiState.email,
                onEmailChange = { onAction(SignUpAction.UpdateEmail(it)) },
                isError = uiState.showEmailError,
                enabled = !uiState.isLoading,
                onFocusLost = { onAction(SignUpAction.EmailFocusLost) }
            )

            PasswordField(
                password = uiState.password,
                onPasswordChange = { onAction(SignUpAction.UpdatePassword(it)) },
                isError = uiState.showPasswordError,
                enabled = !uiState.isLoading,
                rulesUi = uiState.passwordRules
            )

            PasswordField(
                title = R.string.confirm_password,
                label = R.string.confirm_your_password,
                password = uiState.confirmPassword,
                onPasswordChange = { onAction(SignUpAction.UpdateConfirmPassword(it)) },
                isError = !uiState.isConfirmPasswordValid && uiState.confirmPassword.isNotBlank(),
                enabled = !uiState.isLoading,
                rulesUi = emptyList()
            )

            PrimaryButton(
                label = stringResource(R.string.sign_up),
                onClick = { onAction(SignUpAction.SignUpWithEmail) },
                enabled = uiState.canSignUp && !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )

            if (uiState.hasError) {
                Text(
                    text = uiState.errorMessage ?: stringResource(R.string.an_error_occurred),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                )
            }

        }
    }
}

@Preview
@Composable
private fun SignUpFormPreview() {
    MemorifyTheme {
        SignUpForm(
            uiState = SignUpUiState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun SignUpFormErrorPreview() {
    MemorifyTheme {
        SignUpForm(
            uiState = SignUpUiState(
                email = "user@example.com",
                password = "Password123!",
                confirmPassword = "Password123!",
                errorMessage = "Email already exists"
            ),
            onAction = {}
        )
    }
}