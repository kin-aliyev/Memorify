package com.example.feature_auth.presentation.signin.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.memorify.feature_auth.R
import com.example.core_ui.components.BaseButton
import com.example.core_ui.components.Dimens


@Composable
fun SignWithGoogleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean,
    isLoading: Boolean = false,
    @StringRes label: Int = R.string.sign_in_with_google,
) {
    BaseButton(
        label = stringResource(label),
        icon = R.drawable.icon_google_logo,
        onClick = onClick,
        enabled = enabled,
        isLoading = isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceContainerHigh.copy(alpha = 0.38f),
            disabledContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        ),
        loaderColor = MaterialTheme.colorScheme.onSurface,
        modifier = modifier
            .fillMaxWidth()
            .padding(top = Dimens.spacing4)
    )
}