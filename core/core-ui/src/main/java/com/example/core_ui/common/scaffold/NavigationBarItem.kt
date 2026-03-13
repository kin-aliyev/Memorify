package com.example.core_ui.common.scaffold

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_ui.Dimens
import com.example.core_ui.theme.MemorifyTheme

private val IndicatorWidth = 64.dp
private val IndicatorHeight = 32.dp

private val NavAnimationEasing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f)
private const val NavAnimationDuration = 350

@Composable
fun NavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier,
) {
    val contentColor by animateColorAsState(
        targetValue = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant,
        animationSpec = tween(
            durationMillis = NavAnimationDuration,
            easing = NavAnimationEasing,
        ),
        label = "contentColor"
    )

    val indicatorColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        } else {
            Color.Transparent
        },
        animationSpec = tween(
            durationMillis = NavAnimationDuration,
            easing = NavAnimationEasing,
        ),
        label = "indicatorColor",
    )

    val indicatorWidth by animateDpAsState(
        targetValue = if (selected) IndicatorWidth else 0.dp,
        animationSpec = tween(
            durationMillis = NavAnimationDuration,
            easing = NavAnimationEasing,
        ),
        label = "indicatorWidth",
    )

    Column(
        modifier = modifier
            .fillMaxHeight()
            .heightIn(min = Dimens.heightTab)
            .clickable(
                indication = null,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() }),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(height = IndicatorHeight, width = IndicatorWidth)
        ) {
            Box(
                modifier = Modifier
                    .size(width = indicatorWidth, height = IndicatorHeight)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(color = indicatorColor)
            )
            
            Icon(
                imageVector = icon, contentDescription = null,
                tint = contentColor,
                modifier = Modifier.Companion.size(Dimens.iconMd)
            )
        }

        Spacer(Modifier.Companion.height(Dimens.spacing4))

        Text(
            text = label,
            color = contentColor,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
private fun NavigationBarItemPreview() {
    var selectedItem by remember { mutableStateOf(0) }

    MemorifyTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.heightNavigationBar)
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = Dimens.spacing8),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationBarItem(
                selected = selectedItem == 0,
                onClick = { selectedItem = 0 },
                icon = Icons.Default.Home,
                label = "Home"
            )
            NavigationBarItem(
                selected = selectedItem == 1,
                onClick = { selectedItem = 1 },
                icon = Icons.Default.Analytics,
                label = "Analytics"
            )
            NavigationBarItem(
                selected = selectedItem == 2,
                onClick = { selectedItem = 2 },
                icon = Icons.Default.Settings,
                label = "Settings"
            )
        }
    }

}