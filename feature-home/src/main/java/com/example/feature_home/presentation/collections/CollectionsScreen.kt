package com.example.feature_home.presentation.collections

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core_ui.common.scaffold.AppHeader
import com.example.core_ui.common.scaffold.PreviewScaffold
import com.example.core_ui.model.BottomNavItem
import com.example.core_ui.model.TopBarState
import com.example.core_ui.theme.MemorifyTheme
import com.example.feature_home.presentation.collections.components.AddCollectionDialog
import com.example.feature_home.presentation.collections.components.CollectionsScreenContent
import com.example.feature_home.presentation.common.SpeedDialFab
import com.example.feature_home.presentation.common.SpeedDialItem

@Composable
fun CollectionsScreen(
    modifier: Modifier = Modifier,
    viewModel: CollectionsViewModel = hiltViewModel(),
    onSetTopBar: (TopBarState) -> Unit,
    snackbarHostState: SnackbarHostState,
    onNavigateToCollectionDetail: (String) -> Unit,
    onNavigateToAddManual: () -> Unit,
    onNavigateToAddAi: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val errorMessages = rememberCollectionsErrorMessages()

    var fabExpanded by remember { mutableStateOf(false) }
    var showAddCollectionDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        onSetTopBar(
            TopBarState(
                content = { AppHeader(label = "Memorify") },
                fab = {
                    SpeedDialFab(
                        expanded = fabExpanded,
                        onToggle = { fabExpanded = !fabExpanded },
                        items = listOf(
                            SpeedDialItem(
                                icon = Icons.Default.CreateNewFolder,
                                label = "New collection",
                                onClick = {
                                    fabExpanded = false
                                    showAddCollectionDialog = true
                                },
                            ),
                            SpeedDialItem(
                                icon = Icons.Default.Edit,
                                label = "Add word manually",
                                onClick = {
                                    fabExpanded = false
                                    viewModel.onAction(CollectionsAction.OnAddWordManualClick)
                                },
                            ),
                            SpeedDialItem(
                                icon = Icons.Default.AutoAwesome,
                                label = "Generate with AI",
                                onClick = {
                                    fabExpanded = false
                                    viewModel.onAction(CollectionsAction.OnAddWordAiClick)
                                },
                            ),
                        ),
                    )
                },
            )
        )
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvent.collect { event ->
            when (event) {
                CollectionsNavigationEvent.ToAddManual -> onNavigateToAddManual()
                CollectionsNavigationEvent.ToAddAi -> onNavigateToAddAi()

                is CollectionsNavigationEvent.ToCollectionDetail -> onNavigateToCollectionDetail(
                    event.collectionId
                )
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

    if (showAddCollectionDialog) {
        AddCollectionDialog(
            onConfirm = { name, emoji, color ->
                showAddCollectionDialog = false
                viewModel.onAction(CollectionsAction.OnAddCollectionConfirm(name, emoji, color))
            },
            onDismiss = { showAddCollectionDialog = false },
        )
    }

    CollectionsScreenContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun SpeedDialExpandedPreview() {
    MemorifyTheme {
        var showAddCollectionDialog by remember { mutableStateOf(true) }
        PreviewScaffold(
            topBarState = TopBarState(
                content = { AppHeader(label = "Memorify") },
                fab = {
                    var expanded by remember { mutableStateOf(true) }
                    SpeedDialFab(
                        expanded = expanded,
                        onToggle = { expanded = !expanded },
                        items = listOf(
                            SpeedDialItem(
                                icon = Icons.Default.CreateNewFolder,
                                label = "New collection",
                                onClick = {},
                            ),
                            SpeedDialItem(
                                icon = Icons.Default.Edit,
                                label = "Add word manually",
                                onClick = {},
                            ),
                            SpeedDialItem(
                                icon = Icons.Default.AutoAwesome,
                                label = "Generate with AI",
                                onClick = {},
                            ),
                        ),
                    )
                },
            ),
            selectedNavItem = BottomNavItem.Home,
        ) { innerPadding ->
            if (showAddCollectionDialog) {
                AddCollectionDialog(
                    onConfirm = { name, emoji, color -> },
                    onDismiss = { showAddCollectionDialog = false },
                )
            }
            CollectionsScreenContent(
                uiState = CollectionsUiState(collections = emptyList(), isLoading = false),
                onAction = {},
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}