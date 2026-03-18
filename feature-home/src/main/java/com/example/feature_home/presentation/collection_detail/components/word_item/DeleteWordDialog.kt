package com.example.feature_home.presentation.collection_detail.components.word_item

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.theme.MemorifyTheme

@Composable
internal fun DeleteWordDialog(
    modifier: Modifier = Modifier,
    wordName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        icon = {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
            )
        },
        title = { Text(text = "Delete word?") },
        text = {
            Text(
                text = buildAnnotatedString {
                    append("\"")
                    withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append(wordName)
                    }
                    append("\" will be permanently removed from this collection.")
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(text = "Cancel") } },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                ),
            ) {
                Text(text = "Delete")
            }
        }
    )
}

@Preview
@Composable
private fun DeleteWordDialogPreview() {
    MemorifyTheme() {
        DeleteWordDialog(
            onConfirm = {},
            onDismiss = {},
            wordName = "acquire"
        )
    }
}