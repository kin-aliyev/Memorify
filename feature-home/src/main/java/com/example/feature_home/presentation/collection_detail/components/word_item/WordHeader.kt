package com.example.feature_home.presentation.collection_detail.components.word_item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_domain.model.word.WordCard
import com.example.core_ui.Dimens
import com.example.core_ui.mapper.toColor
import com.example.core_ui.theme.MemorifyTheme

@Composable
internal fun WordHeader(
    word: WordCard,
    showTranslation: Boolean,
    knowledgeLevel: KnowledgeLevel,
    levelColor: Color,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            start = Dimens.spacing12,
            end = Dimens.spacing12,
            top = Dimens.spacing12,
            bottom = Dimens.spacing12,
        ),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing4),
    ) {
        WordRow(
            word = word.word,
            isFavorite = word.isFavorite,
            onFavoriteToggle = onFavoriteToggle,
        )

        SecondaryRow(
            translation = word.translation,
            partOfSpeech = word.partOfSpeech,
            showTranslation = showTranslation,
            knowledgeLevel = knowledgeLevel,
            levelColor = levelColor,
        )
    }
}

@Composable
private fun WordRow(
    word: String,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = word,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )

        Spacer(modifier = Modifier.width(Dimens.spacing8))

        Icon(
            imageVector = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
            tint = if (isFavorite) Color(0xFFFFB300) else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .size(Dimens.iconMd)
                .clip(CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onFavoriteToggle,
                ),
        )
    }
}

@Composable
private fun SecondaryRow(
    translation: String,
    partOfSpeech: String,
    showTranslation: Boolean,
    knowledgeLevel: KnowledgeLevel,
    levelColor: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        val hasContent = (showTranslation && translation.isNotBlank()) || partOfSpeech.isNotBlank()

        if (hasContent) {
            TranslationText(
                translation = translation,
                partOfSpeech = partOfSpeech,
                showTranslation = showTranslation,
                modifier = Modifier.weight(1f),
            )
            Spacer(modifier = Modifier.width(Dimens.spacing8))
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        KnowledgeLevelBadge(
            knowledgeLevel = knowledgeLevel,
            levelColor = levelColor,
        )
    }
}

@Composable
private fun TranslationText(
    translation: String,
    partOfSpeech: String,
    showTranslation: Boolean,
    modifier: Modifier = Modifier,
) {
    val text = buildAnnotatedString {
        if (showTranslation && translation.isNotBlank()) { append(translation)
            if (partOfSpeech.isNotBlank()) append(" · ")
        }
        if (partOfSpeech.isNotBlank()) {
            withStyle(SpanStyle(fontStyle = FontStyle.Italic)) { append(partOfSpeech) }
        }
    }

    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}

@Composable
private fun KnowledgeLevelBadge(
    knowledgeLevel: KnowledgeLevel,
    levelColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = levelColor.copy(alpha = 0.15f),
                shape = MaterialTheme.shapes.small,
            )
            .padding(
                horizontal = Dimens.spacing8,
                vertical = Dimens.spacing2,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = knowledgeLevel.name,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = levelColor,
        )
    }
}

// ── Previews ─────────────────────────────────────────────────────────────────
private class WordHeaderPreviewProvider : PreviewParameterProvider<WordCard> {
    override val values = sequenceOf(
        WordCard(
            id = "1",
            word = "acquire",
            translation = "приобретать",
            partOfSpeech = "verb",
            knowledgeLevel = KnowledgeLevel.LEARNING.name,
            isFavorite = true,
        ),
        WordCard(
            id = "2",
            word = "stakeholder",
            translation = "заинтересованная сторона",
            partOfSpeech = "noun",
            knowledgeLevel = KnowledgeLevel.NEW.name,
            isFavorite = false,
        ),
        WordCard(
            id = "3",
            word = "revenue",
            translation = "выручка",
            partOfSpeech = "noun",
            knowledgeLevel = KnowledgeLevel.MASTERED.name,
            isFavorite = true,
        ),
        WordCard(
            id = "4",
            word = "leverage",
            translation = "использовать",
            partOfSpeech = "verb",
            knowledgeLevel = KnowledgeLevel.REVIEWING.name,
            isFavorite = false,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun WordHeaderPreview(
    @PreviewParameter(WordHeaderPreviewProvider::class) word: WordCard,
) {
    MemorifyTheme {
        val knowledgeLevel = KnowledgeLevel.fromString(word.knowledgeLevel)
        WordItem(
            word = word,
            showTranslation = true,
            onEditClick = {},
            onDeleteClick = {},
            onFavoriteToggle = {}
        )
    }
}

@Preview(showBackground = true, name = "Translation Hidden")
@Composable
private fun WordHeaderTranslationHiddenPreview() {
    MemorifyTheme {
        val word = WordCard(
            id = "1",
            word = "acquire",
            translation = "приобретать",
            partOfSpeech = "verb",
            knowledgeLevel = KnowledgeLevel.LEARNING.name,
            isFavorite = true,
        )
        val knowledgeLevel = KnowledgeLevel.fromString(word.knowledgeLevel)
        WordHeader(
            word = word,
            showTranslation = false,
            knowledgeLevel = knowledgeLevel,
            levelColor = knowledgeLevel.toColor(),
            onFavoriteToggle = {},
        )
    }
}
