package com.example.feature_auth.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.feature_auth.presentation.model.PasswordRuleUi
import com.example.memorify.feature_auth.R
import com.example.core_ui.components.Dimens
import com.example.core_ui.components.StandardTextField

@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    @StringRes title: Int = R.string.password,
    @StringRes label: Int = R.string.enter_your_password,
    password: String,
    onPasswordChange: (String) -> Unit,
    rulesUi: List<PasswordRuleUi>,
    isError: Boolean = false,
    enabled: Boolean = true,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    StandardTextField(
        value = password,
        onValueChange = onPasswordChange,
        title = title,
        label = label,
        leadingIcon = { Icon(imageVector = Icons.Filled.Lock, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = painterResource(
                        if (passwordVisible) R.drawable.icon_visibility_off
                        else R.drawable.icon_visibility_on
                    ),
                    contentDescription = stringResource(
                        if (passwordVisible) R.string.hide_password
                        else R.string.show_password
                    )
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        isError = isError,
        enabled = enabled,
        supportingContent = if (isError && rulesUi.isNotEmpty()) {
            { UnsatisfiedPasswordRules(rulesUi) }
        } else null,
        modifier = modifier
    )
}

@Composable
private fun UnsatisfiedPasswordRules(rulesUi: List<PasswordRuleUi>) {
    Column {
        rulesUi.forEach { rule ->
            val color = if (rule.satisfied) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.error

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(
                        if (rule.satisfied) R.drawable.icon_check_small
                        else R.drawable.icon_dot
                    ),
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(Dimens.iconMd)
                )

                Spacer(Modifier.width(Dimens.spacing8))

                Text(rule.message, color = color, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

