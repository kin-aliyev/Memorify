package com.example.feature_home.presentation.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.core_ui.model.TopBarState
import com.example.core_ui.navigation.GraphRoute
import com.example.feature_home.presentation.collection_detail.CollectionDetailScreen
import com.example.feature_home.presentation.collections.CollectionsScreen

fun NavGraphBuilder.homeNavGraph(
    navController: NavController,
    onSetTopBar: (TopBarState) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    navigation<GraphRoute.Home>(startDestination = HomeRoute.Collections) {

        composable<HomeRoute.Collections> {
            CollectionsScreen(
                onSetTopBar = onSetTopBar,
                snackbarHostState = snackbarHostState,

                onNavigateToCollectionDetail = { collectionId ->
                    navController.navigate(route = HomeRoute.CollectionDetail(collectionId))
                },
                onNavigateToAddManual = { navController.navigate(route = HomeRoute.AddEditWord) },
                onNavigateToAddAi = { navController.navigate(route = HomeRoute.AddWordAi) }
            )
        }

        composable<HomeRoute.CollectionDetail> {
            CollectionDetailScreen(
                onSetTopBar = onSetTopBar,
                snackbarHostState = snackbarHostState,

                onNavigateToAddWordManual = { collectionId ->
                    navController.navigate(route = HomeRoute.AddEditWord(collectionId = collectionId))
                },
                onNavigateToAddWordAi = { collectionId ->
                    navController.navigate(route = HomeRoute.AddWordAi(collectionId = collectionId))
                },
                onNavigateToEditWord = { collectionId, wordId ->
                    navController.navigate(
                      route = HomeRoute.AddEditWord(collectionId = collectionId, wordId = wordId)
                    )
                },
                onNavigateBack = {
                    navController.popBackStack(route = HomeRoute.Collections, inclusive = false)
                },
            )
        }
    }
}