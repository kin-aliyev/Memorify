package com.example.memorify.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
    startDestination: GraphRoute,
    onSetTopBar: (TopBarState) -> Unit,
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        // Forward navigation: slide in from right
        enterTransition = {
            slideInHorizontally(tween(300)) { it / 5 } + fadeIn(tween(300))
        },
        // Forward navigation: previous screen slides slightly left
        exitTransition = {
            slideOutHorizontally(tween(300)) { -it / 5 } + fadeOut(tween(200))
        },
        // Back navigation: screen slides back in from left
        popEnterTransition = {
            slideInHorizontally(tween(300)) { -it / 5 } + fadeIn(tween(300))
        },
        // Back navigation: screen slides out to right
        popExitTransition = {
            slideOutHorizontally(tween(300)) { it / 5 } + fadeOut(tween(200))
        },
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