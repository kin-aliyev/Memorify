package com.example.feature_auth.presentation.signin.components

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
import com.example.feature_auth.presentation.signin.SignInAction
import com.example.feature_auth.presentation.signin.SignInUiState
import com.example.memorify.feature_auth.R

@Composable
fun SignInScreenContent(
    uiState: SignInUiState,
    onAction: (SignInAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = Dimens.paddingScreen),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing24),
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                style = MaterialTheme.typography.headlineLarge,
            )

            SignInForm(
                uiState = uiState,
                onAction = onAction,
            )
        }

        LoadingOverlay(
            isLoading = uiState.isLoading,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}