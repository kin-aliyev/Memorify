package com.example.feature_home.presentation.collection_detail.components.word_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.SwapHoriz
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_domain.model.word.WordCard
import com.example.core_ui.Dimens
import com.example.core_ui.theme.MemorifyTheme

@Composable
internal fun WordDetails(
    word: WordCard,
    showTranslation: Boolean,
    modifier: Modifier = Modifier,
) {
    val rows = buildList<@Composable () -> Unit> {
        if (!showTranslation && word.translation.isNotBlank()) {
            add {
                DetailRow(
                    icon = Icons.Outlined.Translate,
                    iconContentDescription = "Translation",
                    text = word.translation,
                )
            }
        }
        if (word.description.isNotBlank()) {
            add {
                DetailRow(
                    icon = Icons.Outlined.Info,
                    iconContentDescription = "Description",
                    text = word.description,
                )
            }
        }
        if (word.exampleSentence.isNotBlank()) {
            add {
                DetailRow(
                    icon = Icons.Outlined.ChatBubbleOutline,
                    iconContentDescription = "Example sentence",
                    text = word.exampleSentence,
                    italic = true,
                )
            }
        }
        if (word.synonyms.isNotEmpty()) {
            add {
                DetailRow(
                    icon = Icons.Outlined.Link,
                    iconContentDescription = "Synonyms",
                    text = word.synonyms.joinToString(" · "),
                )
            }
        }
        if (word.antonyms.isNotEmpty()) {
            add {
                DetailRow(
                    icon = Icons.Outlined.SwapHoriz,
                    iconContentDescription = "Antonyms",
                    text = word.antonyms.joinToString(" · "),
                )
            }
        }
        if (word.collocations.isNotEmpty()) {
            add {
                CollocationRow(collocations = word.collocations)
            }
        }
    }

    if (rows.isEmpty()) return

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = Dimens.spacing12,
                end = Dimens.spacing12,
                top = Dimens.spacing8,
                bottom = Dimens.spacing12,
            ),
    ) {
        rows.forEachIndexed { index, row ->
            row()
            if (index < rows.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = Dimens.spacing8),
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                    thickness = 0.5.dp,
                )
            }
        }
    }
}

@Composable
internal fun DetailRow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconContentDescription: String,
    text: String,
    italic: Boolean = false,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = iconContentDescription,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(Dimens.iconSm),
        )

        Spacer(modifier = Modifier.width(Dimens.spacing8))

        Text(
            text = if (italic) {
                buildAnnotatedString {
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic)) { append(text) }
                }
            } else buildAnnotatedString { append(text) },
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun CollocationRow(
    collocations: List<String>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            imageVector = Icons.Outlined.FormatListBulleted,
            contentDescription = "Collocations",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(Dimens.iconSm),
        )

        Spacer(modifier = Modifier.width(Dimens.spacing8))

        Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacing4)) {
            collocations.forEach { collocation ->
                Text(
                    text = collocation,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

// ── Previews ──────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "All fields")
@Composable
private fun WordDetailsFullPreview() {
    MemorifyTheme {
        WordItem(
            word = WordCard(
                id = "1",
                word = "acquire",
                translation = "приобретать",
                partOfSpeech = "verb",
                description = "To gain something through effort or experience.",
                exampleSentence = "She acquired new skills working abroad.",
                synonyms = listOf("obtain", "gain", "attain"),
                antonyms = listOf("lose", "forfeit"),
                collocations = listOf("acquire knowledge", "acquire skills", "acquire assets"),
                knowledgeLevel = KnowledgeLevel.LEARNING.name,
                isFavorite = true,
            ),
            showTranslation = true,
            onFavoriteToggle = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Translation hidden")
@Composable
private fun WordDetailsTranslationHiddenPreview() {
    MemorifyTheme {
        WordItem(
            word = WordCard(
                id = "1",
                word = "acquire",
                translation = "приобретать",
                partOfSpeech = "verb",
                description = "To gain something through effort or experience.",
                exampleSentence = "She acquired new skills working abroad.",
                synonyms = listOf("obtain", "gain", "attain"),
                antonyms = listOf("lose", "forfeit"),
                collocations = emptyList(),
                knowledgeLevel = KnowledgeLevel.REVIEWING.name,
                isFavorite = false,
            ),
            showTranslation = false,
            onFavoriteToggle = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Minimal fields")
@Composable
private fun WordDetailsMinimalPreview() {
    MemorifyTheme {
        WordItem(
            word = WordCard(
                id = "1",
                word = "leverage",
                translation = "использовать",
                partOfSpeech = "verb",
                knowledgeLevel = KnowledgeLevel.NEW.name,
                isFavorite = false,
            ),
            showTranslation = true,
            onFavoriteToggle = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}