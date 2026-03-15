package com.example.memorify.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.core_ui.model.TopBarState
import com.example.core_ui.navigation.GraphRoute
import com.example.feature_auth.presentation.navigation.authNavGraph
import com.example.feature_home.presentation.navigation.homeNavGraph

@Composable
fun MainNavigation(
    navController: NavHostController,
    onSetTopBar: (TopBarState) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = GraphRoute.Auth,
        modifier = modifier
    ) {

        authNavGraph(
            navController = navController,
            onSetTopBar = onSetTopBar,
            onAuthSuccess = {
                navController.navigate(GraphRoute.Home) {
                    popUpTo<GraphRoute.Auth> { inclusive = true }
                }
            }
        )

        homeNavGraph(
            navController = navController,
            onSetTopBar = onSetTopBar,
            snackbarHostState = snackbarHostState
        )

    }
}