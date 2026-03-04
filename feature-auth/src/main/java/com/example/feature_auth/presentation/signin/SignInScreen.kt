@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.feature_auth.presentation.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.components.AppHeader
import com.example.core_ui.components.Dimens
import com.example.core_ui.components.LoadingOverlay
import com.example.core_ui.theme.MemorifyTheme
import com.example.feature_auth.presentation.signin.components.SignInForm
import com.example.memorify.feature_auth.R

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onAuthSuccess: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            SignInNavigationEvent.NavigateToHome -> {
                onAuthSuccess()
                viewModel.onNavigationHandled()
            }
            SignInNavigationEvent.NavigateToForgotPassword -> {
                onNavigateToForgotPassword()
                viewModel.onNavigationHandled()
            }
            SignInNavigationEvent.NavigateToSignUp -> {
                onNavigateToSignUp()
                viewModel.onNavigationHandled()
            }
            null -> Unit
        }
    }


    SignInScreenContent(
        modifier = modifier,
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Composable
private fun SignInScreenContent(
    modifier: Modifier = Modifier,
    uiState: SignInUiState,
    onAction: (SignInAction) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    AppHeader()
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                        end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                        bottom = innerPadding.calculateBottomPadding()
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(horizontal = Dimens.paddingScreen),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(Dimens.spacing24)
                ) {
                    Text(
                        text = stringResource(R.string.sign_in),
                        style = MaterialTheme.typography.headlineLarge,
                    )

                    SignInForm(
                        uiState = uiState,
                        onAction = onAction
                    )
                }
            }
        }

        LoadingOverlay(
            isLoading = uiState.isLoading,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Preview
@Composable
private fun SignInScreenContentPreview() {
    MemorifyTheme {
        SignInScreenContent(
            uiState = SignInUiState(isLoading = false),
            onAction = { }
        )
    }
}
