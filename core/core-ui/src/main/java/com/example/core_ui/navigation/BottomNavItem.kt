package com.example.core_ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.core_ui.R

enum class BottomNavItem(
    val route: GraphRoute,
    val icon: ImageVector,
    @StringRes val label: Int,
) {
    Home(
        route = GraphRoute.Home,
        icon = Icons.Default.Home,
        label = R.string.nav_home
    ),
    Analytics(
        route = GraphRoute.Analytics,
        icon = Icons.Default.Analytics,
        label = R.string.nav_analytics
    ),
    Settings(
        route = GraphRoute.Settings,
        icon = Icons.Default.Settings,
        label = R.string.nav_settings
    )
}