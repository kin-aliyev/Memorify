package com.example.feature_auth.presentation.signin.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memorify.feature_auth.R
import com.example.core_ui.components.ClickableText
import com.example.core_ui.components.Dimens
import com.example.core_ui.components.Dimens.elevation2
import com.example.core_ui.components.PrimaryButton
import com.example.core_ui.theme.MemorifyTheme
import com.example.feature_auth.presentation.components.EmailField
import com.example.feature_auth.presentation.components.OrDivider
import com.example.feature_auth.presentation.components.PasswordField
import com.example.feature_auth.presentation.signin.SignInAction
import com.example.feature_auth.presentation.signin.SignInUiState

@Composable
fun SignInForm(
    modifier: Modifier = Modifier,
    uiState: SignInUiState,
    onAction: (SignInAction) -> Unit,
) {
    val context = LocalContext.current

    Card(
        elevation = CardDefaults.cardElevation(elevation2),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        ),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing12),
            modifier = Modifier.padding(Dimens.paddingMd)
        ) {
            EmailField(
                email = uiState.email,
                onEmailChange = { onAction(SignInAction.UpdateEmail(it)) },
                onFocusLost = { onAction(SignInAction.EmailFocusLost)},
                isError = uiState.showEmailError,
                enabled = !uiState.isLoading
            )

            PasswordField(
                password = uiState.password,
                onPasswordChange = { onAction(SignInAction.UpdatePassword(it)) },
                isError = uiState.showPasswordError,
                enabled = !uiState.isLoading,
                rulesUi = uiState.passwordRules,
            )

            ClickableText(
                text = R.string.forgot_password,
                enabled = !uiState.isLoading,
                onClick = { onAction(SignInAction.ForgotPassword) },
                modifier = Modifier.align(Alignment.End)
            )

            PrimaryButton(
                label = stringResource(R.string.sign_in),
                onClick = { onAction(SignInAction.SignInWithEmail) },
                enabled = uiState.canSignIn && !uiState.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )

            if (uiState.hasError) {
                Text(
                    text = uiState.errorMessage ?: stringResource(R.string.an_error_occurred),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                )
            }

            ClickableText(
                text = R.string.create_an_account,
                enabled = !uiState.isLoading,
                onClick = { onAction(SignInAction.CreateAccount) },
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            OrDivider()

            SignWithGoogleButton(
                onClick = { onAction(SignInAction.SignInWithGoogle(context)) },
                enabled = !uiState.isLoading,
            )
        }
    }
}

@Preview
@Composable
private fun SignInFormPreview() {
    MemorifyTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SignInForm(
                uiState = SignInUiState(
                    email = "user@example.com",
                    password = "password123",
                    isLoading = false,
                    errorMessage = "Unknown Message",
//                    passwordRules = listOf(
//                        PasswordRuleUi(
//                            PasswordRule.HAS_LOWERCASE,
//                            "One uppercase letter",
//                            false
//                        ),PasswordRuleUi(
//                            PasswordRule.HAS_UPPERCASE,
//                            "One lowercase letter",
//                            false
//                        ),
//                    ),
//                    isPasswordValid = false
                ),
                onAction = { },
            )
        }
    }
}

@Preview()
@Composable
private fun SignInFormLoadingPreview() {
    MemorifyTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            SignInForm(
                uiState = SignInUiState(
                    email = "user@example.com",
                    password = "password123",
                    isLoading = true,
                ),
                onAction = { },
            )
        }
    }
}