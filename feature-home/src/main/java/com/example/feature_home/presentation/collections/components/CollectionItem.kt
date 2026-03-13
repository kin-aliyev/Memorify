package com.example.feature_home.presentation.collections.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core_ui.Dimens

@Composable
fun CollectionCard(
    modifier: Modifier = Modifier,
    label: ,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(Dimens.elevation2)
    ) {

    }

}