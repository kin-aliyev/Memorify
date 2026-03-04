package com.example.core_ui.components

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.core_ui.theme.MemorifyTheme


/**
 * Variants for future extensibility (e.g. Dots, Linear)
 */
enum class LoadingVariant {
    Circular
}

/**
 * Reusable loading indicator with multiple variants
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    variant: LoadingVariant = LoadingVariant.Circular,
    size: Dp = Dimens.iconLg,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 3.dp,
) {
    if (!isLoading) return

    when (variant) {
        LoadingVariant.Circular -> CircularLoadingIndicator(
            modifier = modifier,
            size = size,
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

/**
 * Circular loading indicator with smooth animation
 */
@Composable
private fun CircularLoadingIndicator(
    modifier: Modifier = Modifier,
    size: Dp = Dimens.iconLg,
    color: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 3.dp,
) {
    val infiniteTransition = rememberInfiniteTransition("circular_loading")

    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = LinearEasing),
        ),
        label = "rotation"
    )

    val sweepAngle by infiniteTransition.animateFloat(
        initialValue = 40f,
        targetValue = 270f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sweep"
    )

    Canvas(
        modifier = modifier.size(size)
    ) {
        drawArc(
            color = color,
            startAngle = rotationAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        )
    }
}

/**
 * Full screen loading overlay
 */
@Composable
fun LoadingOverlay(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    message: String? = null,
    variant: LoadingVariant = LoadingVariant.Circular
) {
    if (!isLoading) return

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.scrim.copy(0.32f)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
            ),
            elevation = CardDefaults.cardElevation(Dimens.elevation2)
        ) {
            Column(
                modifier = Modifier.padding(Dimens.spacing24),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimens.paddingMd)
            ) {
                LoadingIndicator(
                    isLoading = true,
                    variant = variant,
                    size = Dimens.iconXl,
                )

                message?.let {
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
private fun LoadingIndicatorPreview() {
    MemorifyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            LoadingIndicator(isLoading = true, variant = LoadingVariant.Circular)

            LoadingOverlay(isLoading = true, message = "Check internet connection")
        }
    }
}