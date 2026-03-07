package com.example.core_ui.deck

import androidx.compose.ui.graphics.Color
import com.example.core_ui.deck.DeckColor

fun String.toDeckComposeColor(): Color = when (this) {
    "ORANGE" -> Color(0xFFFF9800)
    "BLUE"   -> Color(0xFF2196F3)
    "GREEN"  -> Color(0xFF4CAF50)
    "PURPLE" -> Color(0xFF9C27B0)
    "RED"    -> Color(0xFFF44336)
    "TEAL"   -> Color(0xFF009688)
    "PINK"   -> Color(0xFFE91E63)
    "INDIGO" -> Color(0xFF3F51B5)
    else     -> Color(0xFFFF9800) // дефолт ORANGE
}