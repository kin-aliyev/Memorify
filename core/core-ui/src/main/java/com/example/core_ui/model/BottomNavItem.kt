package com.example.core_ui.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CollectionsBookmark
import androidx.compose.material.icons.outlined.Insights
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.rounded.CollectionsBookmark
import androidx.compose.material.icons.rounded.Insights
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.core_ui.R
import com.example.core_ui.navigation.GraphRoute

enum class BottomNavItem(
    val route: GraphRoute,
    val icon: ImageVector,
    val iconSelected: ImageVector,
    @StringRes val labelRes: Int,
) {
    Home(
        route = GraphRoute.Home,
        icon = Icons.Outlined.CollectionsBookmark,
        iconSelected = Icons.Rounded.CollectionsBookmark,
        labelRes = R.string.nav_home,
    ),
    Analytics(
        route = GraphRoute.Analytics,
        icon = Icons.Outlined.Insights,
        iconSelected = Icons.Rounded.Insights,
        labelRes = R.string.nav_analytics,
    ),
    Settings(
        route = GraphRoute.Settings,
        icon = Icons.Outlined.Tune,
        iconSelected = Icons.Rounded.Tune,
        labelRes = R.string.nav_settings,
    ),
}