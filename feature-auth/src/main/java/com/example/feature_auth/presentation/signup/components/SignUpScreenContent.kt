package com.example.feature_auth.presentation.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.core_ui.Dimens
import com.example.core_ui.common.LoadingOverlay
import com.example.feature_auth.presentation.signup.SignUpAction
import com.example.feature_auth.presentation.signup.SignUpUiState
import com.example.memorify.feature_auth.R

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onAction: (SignUpAction) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = Dimens.paddingScreen),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing24)
        ) {
            Text(
                text = stringResource(R.string.sign_up),
                style = MaterialTheme.typography.headlineLarge,
            )

            SignUpForm(
                uiState = uiState,
                onAction = onAction
            )
        }

        LoadingOverlay(
            isLoading = uiState.isLoading,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}