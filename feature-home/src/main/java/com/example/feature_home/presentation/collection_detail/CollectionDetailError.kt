package com.example.feature_home.presentation.collection_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.example.feature_home.R

sealed interface CollectionDetailError {
    data object LoadFailed : CollectionDetailError
    data object DeleteWordFailed : CollectionDetailError
    data object FavoriteUpdateFailed : CollectionDetailError
    data object UpdateCollectionFailed : CollectionDetailError
    data object DeleteCollectionFailed : CollectionDetailError
}

@Composable
fun rememberCollectionDetailErrorMessages(): Map<CollectionDetailError, String> {
    val loadFailed = stringResource(R.string.error_load_failed)
    val deleteWordFailed = stringResource(R.string.error_delete_word_failed)
    val favoriteFailed = stringResource(R.string.error_favorite_update_failed)
    val updateCollectionFailed = stringResource(R.string.error_update_collection_failed)
    val deleteCollectionFailed = stringResource(R.string.error_delete_collection_failed)

    return remember {
        mapOf(
            CollectionDetailError.LoadFailed to loadFailed,
            CollectionDetailError.DeleteWordFailed to deleteWordFailed,
            CollectionDetailError.FavoriteUpdateFailed to favoriteFailed,
            CollectionDetailError.UpdateCollectionFailed to updateCollectionFailed,
            CollectionDetailError.DeleteCollectionFailed to deleteCollectionFailed,
        )
    }
}