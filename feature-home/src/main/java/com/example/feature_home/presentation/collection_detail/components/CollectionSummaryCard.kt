package com.example.feature_home.presentation.collection_detail.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core_domain.model.collection.Collection
import com.example.core_domain.model.word.KnowledgeLevel
import com.example.core_ui.Dimens
import com.example.core_ui.mapper.toColor
import com.example.core_ui.theme.MemorifyTheme
import com.example.core_ui.utils.formatLastUsed
import java.util.concurrent.TimeUnit


@Composable
internal fun CollectionSummaryCard(
    modifier: Modifier = Modifier,
    collection: Collection,
    newCount: Int,
    learningCount: Int,
    reviewingCount: Int,
    masteredCount: Int,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.paddingScreen),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing8)
        ) {
            CollectionTitleRow(
                emoji = collection.emoji,
                name = collection.name,
                totalWords = collection.totalWords
            )

            LastStudiedRow(lastStudiedAt = collection.lastStudiedAt)

            SegmentedProgressBar(
                newCount = newCount,
                learningCount = learningCount,
                reviewingCount = reviewingCount,
                masteredCount = masteredCount,
            )

            LegendRow(
                newCount = newCount,
                learningCount = learningCount,
                reviewingCount = reviewingCount,
                masteredCount = masteredCount,
                totalWords = collection.totalWords,
            )
        }
    }
}

// ── Row 1 — Title ─────────────────────────────────────────────────────────────

@Composable
private fun CollectionTitleRow(
    emoji: String,
    name: String,
    totalWords: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
            )
        }

        Text(
            text = "$totalWords words",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

// ── Row 2 — Last studied ──────────────────────────────────────────────────────

@Composable
private fun LastStudiedRow(
    lastStudiedAt: Long?,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val label = remember(lastStudiedAt) {
        lastStudiedAt?.let { formatLastUsed(context, it) } ?: "Never studied"
    }

    Text(
        text = label,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier,
    )
}

// ── Row 3 — Segmented progress bar ───────────────────────────────────────────

@Composable
private fun SegmentedProgressBar(
    modifier: Modifier = Modifier,
    newCount: Int,
    learningCount: Int,
    reviewingCount: Int,
    masteredCount: Int,
) {
    val total = newCount + learningCount + reviewingCount + masteredCount
    var animationPlayed by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animationPlayed = true }

    val newFraction by animateFloatAsState(
        targetValue = if (animationPlayed && total > 0) newCount.toFloat() / total else 0f,
        animationSpec = tween(durationMillis = 600, delayMillis = 0),
        label = "newFraction"
    )
    val learningFraction by animateFloatAsState(
        targetValue = if (animationPlayed && total > 0) learningCount.toFloat() / total else 0f,
        animationSpec = tween(durationMillis = 600, delayMillis = 100),
        label = "learningFraction",
    )
    val reviewingFraction by animateFloatAsState(
        targetValue = if (animationPlayed && total > 0) reviewingCount.toFloat() / total else 0f,
        animationSpec = tween(durationMillis = 600, delayMillis = 200),
        label = "reviewingFraction",
    )
    val masteredFraction by animateFloatAsState(
        targetValue = if (animationPlayed && total > 0) masteredCount.toFloat() / total else 0f,
        animationSpec = tween(durationMillis = 600, delayMillis = 300),
        label = "masteredFraction",
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.heightProgressBar)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.outlineVariant),
    ) {
        if (total == 0) return@Row

        if (newFraction > 0f) {
            Box(
                modifier = Modifier
                    .weight(newFraction)
                    .height(Dimens.heightProgressBar)
                    .background(KnowledgeLevel.NEW.toColor())
            )
        }

        if (learningFraction > 0f) {
            Box(
                modifier = Modifier
                    .weight(learningFraction)
                    .height(Dimens.heightProgressBar)
                    .background(KnowledgeLevel.LEARNING.toColor()),
            )
        }

        if (reviewingFraction > 0f) {
            Box(
                modifier = Modifier
                    .weight(reviewingFraction)
                    .height(Dimens.heightProgressBar)
                    .background(KnowledgeLevel.REVIEWING.toColor()),
            )
        }

        if (masteredFraction > 0f) {
            Box(
                modifier = Modifier
                    .weight(masteredFraction)
                    .height(Dimens.heightProgressBar)
                    .background( KnowledgeLevel.KNOWN.toColor()),
            )
        }
    }
}

// ── Row 4 — Legend ────────────────────────────────────────────────────────────

@Composable
private fun LegendRow(
    modifier: Modifier = Modifier,
    totalWords: Int,
    newCount: Int,
    learningCount: Int,
    reviewingCount: Int,
    masteredCount: Int,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(Dimens.spacing4),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            LegendDot(color = KnowledgeLevel.NEW.toColor(), label = "$newCount New")
            LegendDot(color = KnowledgeLevel.LEARNING.toColor(), label = "$learningCount Learning")
            LegendDot(color = KnowledgeLevel.REVIEWING.toColor(), label = "$reviewingCount Reviewing")
            LegendDot(color = KnowledgeLevel.KNOWN.toColor(), label = "$masteredCount Known")
        }

        val studiedPercent = if (totalWords > 0) {
            (((totalWords - newCount).toFloat() / totalWords) * 100).toInt()
        } else 0

        Text(
            text = "$studiedPercent% studied",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
private fun LegendDot(
    modifier: Modifier = Modifier,
    color: Color,
    label: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing4),
    ) {
        Box(
            modifier = Modifier
                .size(Dimens.iconXxs)
                .clip(CircleShape)
                .background(color)
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
private fun CollectionSummaryCardPreview() {
    MemorifyTheme {
        CollectionSummaryCard(
            collection = Collection(
                id = "1",
                name = "Business English",
                emoji = "📚",
                lastStudiedAt = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2),
                totalWords = 42,
            ),
            newCount = 18,
            learningCount = 8,
            reviewingCount = 0,
            masteredCount = 16,
        )
    }
}

@Preview(showBackground = true, name = "Empty collection")
@Composable
private fun CollectionSummaryCardEmptyPreview() {
    MemorifyTheme {
        CollectionSummaryCard(
            collection = Collection(
                id = "2",
                name = "Spanish Basics",
                emoji = "🇪🇸",
                lastStudiedAt = null,
                totalWords = 0,
            ),
            newCount = 0,
            learningCount = 0,
            reviewingCount = 0,
            masteredCount = 0,
        )
    }
}