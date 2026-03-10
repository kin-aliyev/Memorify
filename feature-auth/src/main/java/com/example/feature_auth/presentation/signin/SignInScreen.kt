@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.feature_auth.presentation.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.Dimens
import com.example.core_ui.components.LoadingOverlay
import com.example.core_ui.components.scaffold.AppHeader
import com.example.core_ui.components.scaffold.PreviewScaffold
import com.example.core_ui.model.TopBarState
import com.example.core_ui.navigation.BottomNavItem
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
    onSetTopBar: (TopBarState) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        onSetTopBar(
            TopBarState(
                content = {
                    AppHeader(label = stringResource(com.example.core_ui.R.string.app_name))
                }
            )
        )
    }

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


@Preview
@Composable
private fun SignInScreenContentPreview() {
    MemorifyTheme {
        PreviewScaffold(
            topBarState = TopBarState(content = { AppHeader(label = "Memorify") }),
            selectedNavItem = BottomNavItem.Settings
        ) {
            SignInScreenContent(
                uiState = SignInUiState(isLoading = false),
                onAction = {},
                modifier = Modifier.padding(it)
            )
        }
    }
}
