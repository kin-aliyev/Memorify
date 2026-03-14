package com.example.core_ui.deck

import androidx.compose.ui.graphics.Color
import com.example.core_domain.model.deck.CollectionColor

fun CollectionColor.toComposeColor(): Color = when (this) {
    CollectionColor.ORANGE -> Color(0xFFFF9800)
    CollectionColor.BLUE   -> Color(0xFF2196F3)
    CollectionColor.GREEN  -> Color(0xFF4CAF50)
    CollectionColor.PURPLE -> Color(0xFF9C27B0)
    CollectionColor.RED    -> Color(0xFFF44336)
    CollectionColor.TEAL   -> Color(0xFF009688)
    CollectionColor.PINK   -> Color(0xFFE91E63)
    CollectionColor.INDIGO -> Color(0xFF3F51B5)
}