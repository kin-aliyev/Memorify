package com.example.memorify

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.memorify.core.navigation.GraphRoute
import com.example.memorify.feature_auth.presentation.navigation.authNavGraph

@Composable
fun MainNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = GraphRoute.Auth,
        modifier = modifier
    ) {
        authNavGraph(navController)
    }
}