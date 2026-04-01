package com.example.feature_home.presentation.collection_detail.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.core_ui.common.DeleteDialog

@Composable
internal fun DeleteCollectionDialog(
    collectionName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {

    val message = buildAnnotatedString {
        append("\"")
        withStyle(SpanStyle(fontWeight = FontWeight.SemiBold)) {
            append(collectionName)
        }
        append("\" and all its words will be permanently deleted.")
    }.text

    DeleteDialog(
        title = "Delete collection?",
        message = message,
        onConfirm = onConfirm,
        onDismiss = onDismiss,
    )
}