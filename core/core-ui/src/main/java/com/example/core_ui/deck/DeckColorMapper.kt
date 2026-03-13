package com.example.core_ui.deck

import androidx.compose.ui.graphics.Color
import com.example.core_domain.model.deck.DeckColor

fun DeckColor.toComposeColor(): Color = when (this) {
    DeckColor.ORANGE -> Color(0xFFFF9800)
    DeckColor.BLUE   -> Color(0xFF2196F3)
    DeckColor.GREEN  -> Color(0xFF4CAF50)
    DeckColor.PURPLE -> Color(0xFF9C27B0)
    DeckColor.RED    -> Color(0xFFF44336)
    DeckColor.TEAL   -> Color(0xFF009688)
    DeckColor.PINK   -> Color(0xFFE91E63)
    DeckColor.INDIGO -> Color(0xFF3F51B5)
}