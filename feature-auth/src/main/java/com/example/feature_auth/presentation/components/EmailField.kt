package com.example.feature_auth.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.components.StandardTextField
import com.example.core_ui.theme.MemorifyTheme
import com.example.memorify.feature_auth.R

@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    email: String,
    onEmailChange: (String) -> Unit,
    onFocusLost: () -> Unit,
    isError: Boolean = false,
    enabled: Boolean = true
) {
    var wasFocused by remember { mutableStateOf(false) }

    StandardTextField(
        value = email,
        onValueChange = onEmailChange,
        title = R.string.email,
        label = R.string.enter_your_email,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Email,
                contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        isError = isError,
        enabled = enabled,
        modifier = modifier.onFocusChanged { focusState ->
            if (focusState.isFocused) {
                wasFocused = true
            } else if (wasFocused) {
                onFocusLost()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun EmailFieldPreview() {
    MemorifyTheme {
        EmailField(
            email = "example@email.com",
            onEmailChange = {},
            onFocusLost = {}
        )
    }
}
