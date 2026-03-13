package com.example.feature_auth.presentation.signup

import androidx.compose.foundation.layout.padding
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
import com.example.feature_auth.presentation.signup.components.SignUpScreenContent

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    onSetTopBar: (TopBarState) -> Unit,
    onAuthSuccess: () -> Unit,
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
                SignUpNavigationEvent.ToHome -> onAuthSuccess()
            }
        }
    }

    SignUpScreenContent(
        modifier = modifier,
        uiState = uiState,
        onAction = viewModel::onAction
    )
}

@Preview(name = "Sign Up — Default")
@Composable
private fun SignUpPreview() {
    MemorifyTheme {
        PreviewScaffold(
            topBarState = TopBarState(content = { AppHeader(label = "Memorify") }),
        ) { innerPadding ->
            SignUpScreenContent(
                uiState = SignUpUiState(),
                onAction = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}

@Preview(name = "Sign Up — Loading")
@Composable
private fun SignUpLoadingPreview() {
    MemorifyTheme {
        PreviewScaffold(
            topBarState = TopBarState(content = { AppHeader(label = "Memorify") }),
        ) { innerPadding ->
            SignUpScreenContent(
                uiState = SignUpUiState(isLoading = true),
                onAction = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}