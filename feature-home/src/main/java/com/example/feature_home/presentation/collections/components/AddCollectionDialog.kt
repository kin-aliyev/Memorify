package com.example.feature_home.presentation.collections.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_domain.model.collection.CollectionColor
import com.example.core_ui.collection.CollectionEmoji
import com.example.core_ui.Dimens
import com.example.core_ui.collection.toDisplayColor
import com.example.core_ui.common.PrimaryButton
import com.example.core_ui.theme.MemorifyTheme

@Composable
fun AddCollectionDialog(
    onConfirm: (name: String, emoji: String, color: CollectionColor) -> Unit,
    onDismiss: () -> Unit,
) {
    var selectedEmoji by rememberSaveable { mutableStateOf(CollectionEmoji.all.first()) }
    var selectedColor by rememberSaveable { mutableStateOf(CollectionColor.ORANGE) }
    var name by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "New collection",
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimens.spacing16)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.spacing8)
                ) {
                    Text(
                        text = "Choose emoji",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    val rows = 3
                    val itemHeight = Dimens.emojiPickerItem
                    val spacing = Dimens.spacing8
                    val gridHeight = itemHeight * rows + spacing * (rows - 1)

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(6),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(gridHeight),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacing8),
                    ) {
                        items(CollectionEmoji.all) { emoji ->
                            EmojiItem(
                                emoji = emoji,
                                isSelected = emoji == selectedEmoji,
                                onClick = { selectedEmoji = emoji },
                            )
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacing8)) {
                    Text(
                        text = "Choose color",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing8),
                    ) {
                        CollectionColor.entries.forEach { color ->
                            ColorItem(
                                color = color,
                                isSelected = color == selectedColor,
                                onClick = { selectedColor = color },
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = {
                        Text(text = "Collection name", style = MaterialTheme.typography.bodyMedium)
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        confirmButton = {
            PrimaryButton(
                label = "Create",
                onClick = { onConfirm(name.trim(), selectedEmoji, selectedColor) },
                enabled = name.isNotBlank(),
            )
        }
    )
}

@Composable
private fun ColorItem(
    modifier: Modifier = Modifier,
    color: CollectionColor,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val shape = CircleShape
    Box(
        modifier = modifier
            .size(Dimens.colorPickerItem)
            .clip(shape)
            .background(color.toDisplayColor())
            .then(
                if (isSelected) Modifier.border(
                    width = Dimens.borderSelected,
                    color = Color.White,
                    shape = shape,
                ) else Modifier
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(Dimens.iconSm),
            )
        }
    }
}

@Composable
fun EmojiItem(
    modifier: Modifier = Modifier,
    emoji: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val shape = MaterialTheme.shapes.small

    val borderColor = if (isSelected)  MaterialTheme.colorScheme.primary 
        else MaterialTheme.colorScheme.onSurfaceVariant

    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surfaceContainerHigh

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(Dimens.emojiPickerItem)
            .clip(shape)
            .background(backgroundColor)
            .border(
                width = if (isSelected) Dimens.borderSelected else Dimens.borderDefault,
                color = borderColor,
                shape = shape,
            )
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick,
            )
    ) {
        Text(
            text = emoji,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview
@Composable
private fun AddCollectionDialogPreview() {
    MemorifyTheme {
        AddCollectionDialog(
            onConfirm = { _, _, _ -> },
            onDismiss = {},
        )
    }
}