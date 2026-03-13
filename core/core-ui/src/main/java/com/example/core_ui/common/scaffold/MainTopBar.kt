package com.example.core_ui.common.scaffold

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.core_ui.model.TopBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    state: TopBarState,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    if (state.content != null) {
        state.content.invoke()
    } else {
        TopAppBar(
            modifier = modifier,
            title = {
                Text(
                    text = state.title,
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            navigationIcon = { state.navigationIcon?.invoke() },
            actions = { state.actions?.invoke() },
            colors = TopAppBarDefaults.topAppBarColors(),
            scrollBehavior = scrollBehavior,
        )
    }
}