package com.example.memorify

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core_ui.common.scaffold.MainBottomBar
import com.example.core_ui.common.scaffold.MainTopBar
import com.example.core_ui.model.TopBarState
import com.example.core_ui.navigation.GraphRoute
import com.example.memorify.navigation.MainNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()
    val destination = startDestination ?: return

    val navController = rememberNavController()
    var topBarState by remember { mutableStateOf(TopBarState()) }
    val snackbarHostState = remember { SnackbarHostState() }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val showBottomBar = currentBackStackEntry?.destination?.hierarchy
        ?.none { it.hasRoute(GraphRoute.Auth::class) } ?: false

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MainTopBar(state = topBarState) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            if (showBottomBar) {
                MainBottomBar(
                    currentDestination = currentBackStackEntry?.destination,
                    onNavigate = { graphRoute ->
                        navController.navigate(graphRoute) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        floatingActionButton = { topBarState.fab?.invoke() }
    ) { innerPadding ->
        MainNavigation(
            startDestination = destination,
            navController = navController,
            onSetTopBar = { topBarState = it },
            snackbarHostState = snackbarHostState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}