package com.example.core_ui.collection

import androidx.compose.ui.graphics.Color
import com.example.core_domain.model.collection.CollectionColor

fun CollectionColor.toDisplayColor(): Color = when (this) {
    CollectionColor.ORANGE -> Color(0xFFFF9800)
    CollectionColor.BLUE   -> Color(0xFF2196F3)
    CollectionColor.GREEN  -> Color(0xFF4CAF50)
    CollectionColor.PURPLE -> Color(0xFF9C27B0)
    CollectionColor.RED    -> Color(0xFFE53935)
    CollectionColor.TEAL   -> Color(0xFF009688)
    CollectionColor.PINK   -> Color(0xFFE91E63)
    CollectionColor.INDIGO -> Color(0xFF3F51B5)
}

fun CollectionColor.toContainerColor(): Color = when (this) {
    CollectionColor.ORANGE -> Color(0xFFFFF3E0)
    CollectionColor.BLUE   -> Color(0xFFE3F2FD)
    CollectionColor.GREEN  -> Color(0xFFE8F5E9)
    CollectionColor.PURPLE -> Color(0xFFF3E5F5)
    CollectionColor.RED    -> Color(0xFFFFEBEE)
    CollectionColor.TEAL   -> Color(0xFFE0F2F1)
    CollectionColor.PINK   -> Color(0xFFFCE4EC)
    CollectionColor.INDIGO -> Color(0xFFE8EAF6)
}