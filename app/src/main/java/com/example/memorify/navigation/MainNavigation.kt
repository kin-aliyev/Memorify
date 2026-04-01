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
        enterTransition = {
            slideInHorizontally(animationSpec = tween(durationMillis = 300)) { it / 5 } +
                    fadeIn(animationSpec = tween(durationMillis = 300))
        },
        exitTransition = {
            slideOutHorizontally(animationSpec = tween(durationMillis = 300)) { -it / 5 } +
                    fadeOut(animationSpec = tween(durationMillis = 200))
        },
        popEnterTransition = {
            slideInHorizontally(animationSpec = tween(durationMillis = 300)) { -it / 5 } +
                    fadeIn(animationSpec = tween(durationMillis = 300))
        },
        popExitTransition = {
            slideOutHorizontally(animationSpec = tween(durationMillis = 300)) { it / 5 } +
                    fadeOut(animationSpec = tween(durationMillis = 200))
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