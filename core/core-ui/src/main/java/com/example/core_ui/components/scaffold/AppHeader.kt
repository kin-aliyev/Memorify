package com.example.core_ui.components.scaffold

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.core_ui.R
import com.example.core_ui.Dimens

@Composable
fun AppHeader(modifier: Modifier = Modifier, label: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = Dimens.heightTopAppBar)
    ) {
        Image(
            painter = painterResource(R.drawable.image_app_logo),
            contentDescription = "App Logo",
            modifier = Modifier.Companion.size(Dimens.iconXl),
        )

        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
        )
    }
}