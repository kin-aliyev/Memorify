package com.example.memorify

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
internal fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    var topBarState by remember { mutableStateOf(TopBarState()) }

    val showBottomBar = currentBackStackEntry?.destination?.hierarchy
        ?.none { it.hasRoute(GraphRoute.Auth::class) } ?: false

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MainTopBar(state = topBarState) },
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
            navController = navController,
            onSetTopBar = { topBarState = it },
            modifier = Modifier.padding(innerPadding)
        )
    }
}