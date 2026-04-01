package com.example.feature_home.presentation.collection_detail.components

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
fun CollectionDetailSkeleton(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing12),
        contentPadding = PaddingValues(horizontal = Dimens.paddingScreen),
        userScrollEnabled = false,
    ) {
        item { ShimmerBox(height = 140.dp, shape = MaterialTheme.shapes.large) }       // SummaryCard
        item { ShimmerBox(height = 48.dp, shape = MaterialTheme.shapes.medium) }       // FilterBar
        repeat(5) {
            item { ShimmerBox(height = 80.dp, shape = MaterialTheme.shapes.medium) }   // WordItems
        }
    }
}