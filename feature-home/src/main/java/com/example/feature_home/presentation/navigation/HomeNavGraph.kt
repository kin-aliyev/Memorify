package com.example.feature_home.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.example.core_ui.model.TopBarState
import com.example.core_ui.navigation.GraphRoute

fun NavGraphBuilder.homeNavGraph(
    onSetTopBar: (TopBarState) -> Unit,
) {
    navigation<GraphRoute.Home>(startDestination = HomeRoute.Home) {

    }
}