package com.example.feature_home.presentation.collections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.example.feature_home.R

sealed interface CollectionsError {
    data object LoadFailed : CollectionsError
    data object CreateFailed : CollectionsError
    data object NetworkError : CollectionsError
}

@Composable
fun rememberCollectionsErrorMessages(): Map<CollectionsError, String> {
    val load = stringResource(R.string.error_load_collections)
    val create = stringResource(R.string.error_create_collection)
    val network = stringResource(R.string.error_network)
    return remember {
        mapOf(
            CollectionsError.LoadFailed to load,
            CollectionsError.CreateFailed to create,
            CollectionsError.NetworkError to network,
        )
    }
}