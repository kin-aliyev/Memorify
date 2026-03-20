package com.example.feature_home.presentation.collection_detail

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.model.TopBarState

@Composable
fun CollectionDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: CollectionDetailViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onSetTopBar: (TopBarState) -> Unit,
    onNavigateToAddWordManual: (collectionId: String) -> Unit,
    onNavigateToAddWordAi: (collectionId: String) -> Unit,
    onNavigateToEditWord: (collectionId: String, wordId: String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorMessages = rememberCollectionDetailErrorMessages()

    var fabExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.collection, uiState.showTranslation) {
        onSetTopBar(
            TopBarState(
                title = uiState.collection?.let { "${it.emoji} ${it.name}" } ?: "Collection",
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

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                is CollectionDetailNavigationEvent.ToAddWordAi -> onNavigateToAddWordAi(event.collectionId)
                is CollectionDetailNavigationEvent.ToAddWordManual -> onNavigateToAddWordManual(event.collectionId)
                is CollectionDetailNavigationEvent.ToEditWord -> onNavigateToEditWord(event.collectionId, event.wordId)
                CollectionDetailNavigationEvent.Back -> onNavigateBack()
                CollectionDetailNavigationEvent.ToEditCollection -> { }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.errorEvent.collect { error ->
            snackbarHostState.showSnackbar(
                message = errorMessages[error] ?: return@collect,
                duration = SnackbarDuration.Short
            )
        }
    }
}