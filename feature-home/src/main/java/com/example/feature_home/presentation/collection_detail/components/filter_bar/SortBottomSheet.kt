package com.example.feature_home.presentation.collection_detail.components.filter_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.SwapVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.Dimens
import com.example.core_ui.theme.MemorifyTheme
import com.example.feature_home.presentation.collection_detail.model.WordSortOption
import com.example.feature_home.presentation.mapper.displayLabel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    currentSortOption: WordSortOption,
    onSortOptionSelected: (WordSortOption) -> Unit,
    onDismiss: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Text(
                text = "Sort by",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .padding(
                        horizontal = Dimens.paddingScreen,
                        vertical = Dimens.spacing12,
                    )
                    .align(Alignment.CenterHorizontally),
            )

            WordSortOption.entries.forEach { option ->
                SortOptionRow(
                    option = option,
                    selected = option == currentSortOption,
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            onSortOptionSelected(option)
                            onDismiss()
                        }
                    }
                )

            }
        }
    }
}

@Composable
private fun SortOptionRow(
    option: WordSortOption,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                horizontal = Dimens.paddingScreen,
                vertical = Dimens.spacing12,
            )
    ) {
        Icon(
            imageVector = Icons.Outlined.SwapVert,
            contentDescription = null,
            tint = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.width(Dimens.spacing12))

        Text(
            text = option.displayLabel(),
            style = MaterialTheme.typography.bodyLarge,
            color = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )

        if (selected) {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, name = "Sort sheet – Newest selected")
@Composable
private fun SortBottomSheetPreview() {
    MemorifyTheme {
        SortBottomSheet(
            currentSortOption = WordSortOption.NEWEST_FIRST,
            onSortOptionSelected = {},
            onDismiss = {}
        )
    }
}