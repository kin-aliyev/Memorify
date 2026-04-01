package com.example.feature_home.presentation.collection_detail.components.word_item

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.common.DeleteDialog
import com.example.core_ui.theme.MemorifyTheme

@Composable
internal fun DeleteWordDialog(
    wordName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    val message = buildAnnotatedString {
        append("\"")
        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append(wordName)
        }
        append("\" will be permanently removed from this collection.")
    }.text

    DeleteDialog(
        title = "Delete word?",
        message = message,
        onConfirm = onConfirm,
        onDismiss = onDismiss,
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