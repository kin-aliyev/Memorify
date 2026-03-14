package com.example.feature_home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core_ui.model.TopBarState
import com.example.core_ui.navigation.GraphRoute
import com.example.feature_home.presentation.collections.CollectionsScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    onSetTopBar: (TopBarState) -> Unit,
) {
    navigation<GraphRoute.Home>(startDestination = HomeRoute.Collections) {

        composable<HomeRoute.Collections> {
            CollectionsScreen(
                onSetTopBar = onSetTopBar,
                onNavigateToCollectionDetail = { collectionId ->
                    navController.navigate(HomeRoute.CollectionDetail(collectionId))
                },
                onNavigateToAddCollection = {navController.navigate(HomeRoute.AddCollection)},
                onNavigateToAddManual = { navController.navigate(HomeRoute.AddWordManual)},
                onNavigateToAddAi = { navController.navigate(HomeRoute.AddWordAi)}
            )
        }
    }
}