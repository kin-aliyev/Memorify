package com.example.memorify

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.core_ui.components.MainBottomBar
import com.example.core_ui.navigation.GraphRoute

@Composable
internal fun MainScreen() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()

    val showBottomBar = currentBackStackEntry?.destination?.hierarchy
        ?.none { it.hasRoute(GraphRoute.Auth::class) } ?: false

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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
        }
    ) { innerPadding ->
        MainNavigation(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}