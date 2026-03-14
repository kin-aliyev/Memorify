package com.example.core_ui.common.scaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.core_ui.Dimens
import com.example.core_ui.model.BottomNavItem
import com.example.core_ui.model.TopBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScaffold(
    topBarState: TopBarState = TopBarState(),
    selectedNavItem: BottomNavItem? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MainTopBar(state = topBarState) },
        bottomBar = {
            if (selectedNavItem != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .padding(horizontal = Dimens.spacing12)
                ) {
                    NavigationBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.heightNavigationBar)
                            .clip(RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)),
                        windowInsets = WindowInsets(0),
                        containerColor = Color.Transparent
                    ) {
                        BottomNavItem.entries.forEach { item ->
                            NavigationBarItem(
                                selected = item == selectedNavItem,
                                onClick = {},
                                icon = item.icon,
                                iconSelected = item.iconSelected,
                                label = stringResource(item.labelRes),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = { topBarState.fab?.invoke() },
        content = content
    )
}
