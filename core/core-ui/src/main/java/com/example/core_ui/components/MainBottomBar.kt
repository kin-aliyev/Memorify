package com.example.core_ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.core_ui.R
import com.example.core_ui.navigation.BottomNavItem
import com.example.core_ui.navigation.GraphRoute

@Composable
fun MainBottomBar(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onNavigate: (GraphRoute) -> Unit,
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.heightNavigationBar),
        windowInsets = WindowInsets(0),
    ) {
        BottomNavItem.entries.forEach { item ->
            val isSelected = currentDestination?.hierarchy?.any { destination ->
                when(item) {
                    BottomNavItem.Home -> destination.hasRoute<GraphRoute.Home>()
                    BottomNavItem.Analytics -> destination.hasRoute<GraphRoute.Analytics>()
                    BottomNavItem.Settings -> destination.hasRoute<GraphRoute.Settings>()
                }
            } ?: false

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.route) },
                icon = item.icon,
                label = stringResource(item.label)
            )
        }
    }
}