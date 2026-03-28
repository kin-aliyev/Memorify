package com.example.feature_home.presentation.collection_detail.components.word_item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_domain.model.word.WordCard
import com.example.core_ui.Dimens
import com.example.core_ui.mapper.toColor
import com.example.core_ui.theme.MemorifyTheme

@Composable
fun WordItem(
    modifier: Modifier = Modifier,
    word: WordCard,
    showTranslation: Boolean,
    onFavoriteToggle: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    val hasExpandableContent = word.hasExpandableContent(showTranslation)

    val knowledgeLevel = KnowledgeLevel.fromString(word.knowledgeLevel)
    val levelColor = knowledgeLevel.toColor()

    val headerBackground by animateColorAsState(
        targetValue = if (isExpanded) levelColor.copy(alpha = 0.10f)
            .compositeOver(MaterialTheme.colorScheme.surfaceContainerLow)
        else MaterialTheme.colorScheme.surfaceContainerLow,
        animationSpec = tween(durationMillis = 300),
        label = "headerBackground"
    )

    Card(
        onClick = { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
    ) {
        Column {
            // ── Section 1: Word Header ──
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .background(color = headerBackground)
            ) {
                Box(
                    modifier = Modifier
                        .width(Dimens.colorStripe)
                        .fillMaxHeight()
                        .background(levelColor)
                )

                WordHeader(
                    word = word,
                    showTranslation = showTranslation,
                    knowledgeLevel = knowledgeLevel,
                    levelColor = levelColor,
                    onFavoriteToggle = onFavoriteToggle,
                    modifier = Modifier.weight(1f),
                )
            }

            // ── Section 2: Details ──
            AnimatedVisibility(visible = isExpanded && hasExpandableContent) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                ) {
                    Box(
                        modifier = Modifier
                            .width(Dimens.colorStripe)
                            .fillMaxHeight()
                            .background(levelColor.copy(alpha = 0.20f)),
                    )
                    WordDetails(
                        word = word,
                        showTranslation = showTranslation,
                        modifier = Modifier.weight(1f),
                    )
                }
            }

            // ── Section 3: Actions ──
            AnimatedVisibility(visible = isExpanded) {
                WordActions(
                    onDeleteClick = { showDeleteDialog = true },
                    onEditClick = onEditClick
                )
            }
        }
    }

    if (showDeleteDialog) {
        DeleteWordDialog(
            wordName = word.word,
            onConfirm = {
                showDeleteDialog = false
                onDeleteClick()
            },
            onDismiss = { showDeleteDialog = false }
        )
    }
}

// ── Extension ─────────────────────────────────────────────────────────────────

private fun WordCard.hasExpandableContent(showTranslation: Boolean): Boolean =
    (!showTranslation && translation.isNotBlank())
            || description.isNotBlank()
            || exampleSentence.isNotBlank()
            || synonyms.isNotEmpty()
            || antonyms.isNotEmpty()
            || collocations.isNotEmpty()

// ── Preview parameter provider ────────────────────────────────────────────────

private class WordItemPreviewProvider : PreviewParameterProvider<WordCard> {
    override val values = sequenceOf(
        // Rich word — all fields filled, LEARNING level
        WordCard(
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
        // Minimal — only word + translation, NEW level, not favorite
        WordCard(
            id = "2",
            word = "leverage",
            translation = "использовать в своих целях",
            knowledgeLevel = KnowledgeLevel.NEW.name,
            isFavorite = false,
        ),
        // No expandable content at all — card tap should do nothing
        WordCard(
            id = "3",
            word = "ROI",
            translation = "рентабельность инвестиций",
            knowledgeLevel = KnowledgeLevel.KNOWN.name,
            isFavorite = false,
        ),
        // REVIEWING level, example sentence only
        WordCard(
            id = "4",
            word = "delegate",
            translation = "делегировать",
            partOfSpeech = "verb",
            exampleSentence = "A good manager knows how to delegate effectively.",
            knowledgeLevel = KnowledgeLevel.REVIEWING.name,
            isFavorite = true,
        ),
    )
}

// ── Previews ──────────────────────────────────────────────────────────────────

@Preview(showBackground = true, name = "Translation visible")
@Composable
private fun WordItemTranslationVisiblePreview(
    @PreviewParameter(WordItemPreviewProvider::class) word: WordCard,
) {
    MemorifyTheme {
        WordItem(
            word = word,
            showTranslation = true,
            onFavoriteToggle = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}

@Preview(showBackground = true, name = "Translation hidden")
@Composable
private fun WordItemTranslationHiddenPreview(
    @PreviewParameter(WordItemPreviewProvider::class) word: WordCard,
) {
    MemorifyTheme {
        WordItem(
            word = word,
            showTranslation = false,
            onFavoriteToggle = {},
            onEditClick = {},
            onDeleteClick = {},
        )
    }
}