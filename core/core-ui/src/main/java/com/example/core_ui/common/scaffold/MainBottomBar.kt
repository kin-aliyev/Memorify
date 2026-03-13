package com.example.core_ui.components.scaffold

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.core_ui.Dimens
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
            .height(Dimens.heightNavigationBar)
            .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)),
        windowInsets = WindowInsets(0),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
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
                label = stringResource(item.labelRes),
                modifier = Modifier.weight(1f),
            )
        }
    }
}