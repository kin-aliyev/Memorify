package com.example.feature_home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.core_ui.navigation.GraphRoute

fun NavGraphBuilder.homeNavGraph(
) {
    navigation<GraphRoute.Home>(startDestination = HomeRoute.Home) {

    }
}