package com.example.feature_home.presentation.collections.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_domain.model.collection.CollectionColor
import com.example.core_ui.Dimens
import com.example.core_ui.collection.toDisplayColor
import com.example.core_ui.theme.MemorifyTheme
import com.example.core_ui.utils.formatLastUsed

@Composable
fun CollectionItem(
    label: String,
    emoji: String,
    color: CollectionColor,
    reviewedWords: Int,
    totalWords: Int,
    lastUsedDate: Long,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val formattedDate = remember(lastUsedDate) { formatLastUsed(context, lastUsedDate) }
    val learnedPercentage = if (totalWords == 0) 0 else (reviewedWords * 100) / totalWords

    Card(
        onClick = onClick,
        elevation = CardDefaults.cardElevation(Dimens.elevation2),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainerLow),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = Dimens.heightCollectionItem),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .width(Dimens.colorStripe)
                    .fillMaxHeight()
                    .background(color.toDisplayColor()),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = Dimens.heightCollectionItem)
                    .padding(horizontal = Dimens.spacing16, vertical = Dimens.spacing8),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = emoji, style = MaterialTheme.typography.titleLarge)
                        Text(text = label, style = MaterialTheme.typography.titleMedium)
                    }
                    Text(
                        text = "$learnedPercentage%",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Text(
                        text = "$reviewedWords / $totalWords",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CollectionItemPreview() {
    MemorifyTheme {
//        PreviewScaffold(
//            topBarState = TopBarState(content = { AppHeader(label = "Memorify") }),
//            selectedNavItem = BottomNavItem.Home
//        ) {
        CollectionItem(
            label = "Words Collection",
            emoji = "📚",
            color = CollectionColor.ORANGE,
            lastUsedDate = 1766620800,
            reviewedWords = 17,
            totalWords = 100,
            onClick = { },
            modifier = Modifier
//                    .padding(it)
                .padding(horizontal = Dimens.paddingScreen)
        )
//        }
    }
}