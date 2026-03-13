@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.feature_auth.presentation.signin

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.common.scaffold.AppHeader
import com.example.core_ui.common.scaffold.PreviewScaffold
import com.example.core_ui.model.TopBarState
import com.example.core_ui.theme.MemorifyTheme
import com.example.feature_auth.presentation.signin.components.SignInScreenContent

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
            TopBarState(content = { AppHeader(label = stringResource(com.example.core_ui.R.string.app_name)) })
        )
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                SignInNavigationEvent.ToHome -> onAuthSuccess()
                SignInNavigationEvent.ToSignUp -> onNavigateToSignUp()
                SignInNavigationEvent.ToForgotPassword -> onNavigateToForgotPassword()
            }
        }
    }

    SignInScreenContent(
        modifier = modifier,
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Preview(name = "Sign In — Default")
@Composable
private fun SignInPreview() {
    MemorifyTheme {
        PreviewScaffold(
            topBarState = TopBarState(content = { AppHeader(label = "Memorify") }),
        ) { innerPadding ->
            SignInScreenContent(
                uiState = SignInUiState(),
                onAction = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Preview(name = "Sign In — Loading")
@Composable
private fun SignInLoadingPreview() {
    MemorifyTheme {
        PreviewScaffold(
            topBarState = TopBarState(content = { AppHeader(label = "Memorify") }),
        ) { innerPadding ->
            SignInScreenContent(
                uiState = SignInUiState(isLoading = true),
                onAction = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
