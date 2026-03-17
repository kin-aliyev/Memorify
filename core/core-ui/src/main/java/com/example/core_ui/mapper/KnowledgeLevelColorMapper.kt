package com.example.core_ui.mapper

import androidx.compose.ui.graphics.Color
import com.example.core_domain.model.word.KnowledgeLevel

fun KnowledgeLevel.toColor(): Color = when(this) {
    KnowledgeLevel.NEW -> Color(0xFF7986CB)
    KnowledgeLevel.LEARNING -> Color(0xFFFFB300)
    KnowledgeLevel.REVIEWING -> Color(0xFF26A69A)
    KnowledgeLevel.MASTERED -> Color(0xFF66BB6A)
}

