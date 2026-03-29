package com.example.core_ui.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_ui.R

@Composable
fun KnowledgeLevel.displayName(): String = when (this) {
    KnowledgeLevel.NEW -> stringResource(R.string.knowledge_level_new)
    KnowledgeLevel.LEARNING -> stringResource(R.string.knowledge_level_learning)
    KnowledgeLevel.REVIEWING -> stringResource(R.string.knowledge_level_reviewing)
    KnowledgeLevel.KNOWN -> stringResource(R.string.knowledge_level_known)
}