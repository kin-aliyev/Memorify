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
import androidx.compose.ui.unit.dp
import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_domain.model.word.WordCard
import com.example.core_ui.Dimens
import com.example.core_ui.mapper.toColor

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
            AnimatedVisibility(visible = isExpanded) {
                Row {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .fillMaxHeight()
                            .background(levelColor.copy(alpha = 0.20f))
                    )

                    // WordDetails content
                }
            }

            // ── Section 3: Actions ──
            AnimatedVisibility(visible = isExpanded) {
                // WordActions content next
            }
        }
    }
}
