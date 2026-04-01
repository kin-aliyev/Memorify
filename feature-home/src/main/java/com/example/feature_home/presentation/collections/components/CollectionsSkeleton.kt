package com.example.feature_home.presentation.collections.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.core_ui.Dimens
import com.example.core_ui.common.ShimmerBox

@Composable
fun CollectionsSkeleton(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing12),
        contentPadding = PaddingValues(horizontal = Dimens.paddingScreen),
        userScrollEnabled = false
    ) {
        item { ShimmerBox(height = 40.dp, shape = MaterialTheme.shapes.small) }
        repeat(5) {
            item { ShimmerBox(height = Dimens.heightCollectionItem) }
        }
    }
}