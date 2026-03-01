package com.example.memorify.core.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    BaseButton(
        label = label,
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        enabled = enabled,
        isLoading = isLoading,
        colors = ButtonDefaults.buttonColors(),
        loaderColor = MaterialTheme.colorScheme.onPrimary,
    )
}

@Composable
fun SecondaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    BaseButton(
        label = label,
        onClick = onClick,
        modifier = modifier,
        icon = icon,
        enabled = enabled,
        isLoading = isLoading,
        colors = ButtonDefaults.filledTonalButtonColors(),
        loaderColor = MaterialTheme.colorScheme.onSecondaryContainer,
    )
}

@Composable
fun ClickableText(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    onClick: () -> Unit,
    enabled: Boolean = true,
    style: TextStyle = MaterialTheme.typography.labelMedium,
) {
    val color = if (enabled) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    }

    Text(
        text = stringResource(text),
        style = style,
        color = color,
        modifier = modifier.clickable(
            enabled = enabled,
            onClick = onClick,
            role = Role.Button
        )
    )
}

@Composable
internal fun BaseButton(
    label: String,
    onClick: () -> Unit,
    colors: ButtonColors,
    loaderColor: Color,
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    Button(
        onClick = onClick,
        modifier = modifier.heightIn(min = Dimens.heightButton),
        enabled = enabled && !isLoading,
        colors = colors,
    ) {
        if (isLoading) {
            LoadingIndicator(
                isLoading = true,
                color = loaderColor,
                size = Dimens.iconMd,
                strokeWidth = 2.dp,
            )
        } else {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                icon?.let { iconRes ->
                    Icon(
                        painter = painterResource(iconRes),
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.iconMd),
                    )
                    Spacer(modifier = Modifier.width(Dimens.spacing8))
                }
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}
