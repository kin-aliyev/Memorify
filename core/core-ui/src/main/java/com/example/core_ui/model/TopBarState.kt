package com.example.core_ui.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
data class TopBarState(
    val title: String = "",
    val navigationIcon: (@Composable () -> Unit)? = null,
    val actions: (@Composable () -> Unit)? = null,
    val fab: (@Composable () -> Unit)? = null,
    // Произвольный контент — полностью заменяет стандартный TopAppBar
    val content: (@Composable () -> Unit)? = null,
)