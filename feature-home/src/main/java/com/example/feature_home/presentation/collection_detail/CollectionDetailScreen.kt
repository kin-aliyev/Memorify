package com.example.feature_home.presentation.collection_detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.model.TopBarState

@Composable
fun CollectionDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CollectionDetailViewModel = hiltViewModel(),
    onSetTopBar: (TopBarState) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        onSetTopBar(
            TopBarState(
                title = "${uiState.collection?.name} ${uiState.collection?.emoji}",
                actions = {
                    IconButton(onClick = { viewModel.onAction(CollectionDetailAction.OnToggleTranslation)}) {
                        Icon(
                            imageVector = if (uiState.showTranslation) Icons.Outlined.Visibility
                                else Icons.Outlined.VisibilityOff,
                            contentDescription = "Toggle translation",
                        )
                    }
                }
            )
        )
    }
}