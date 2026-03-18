package com.example.feature_home.presentation.collection_detail.components.word_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.core_ui.Dimens
import com.example.core_ui.theme.MemorifyTheme

@Composable
internal fun WordActions(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                end = Dimens.spacing8,
                bottom = Dimens.spacing8,
            ),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextButton(
            onClick = onEditClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
            ),
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier.size(Dimens.iconSm),
            )
            Spacer(modifier = Modifier.size(Dimens.spacing4))
            Text(
                text = "Edit",
                style = MaterialTheme.typography.labelMedium,
            )
        }

        TextButton(
            onClick = onDeleteClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.error,
            ),
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                modifier = Modifier.size(Dimens.iconSm),
            )
            Spacer(modifier = Modifier.size(Dimens.spacing4))
            Text(
                text = "Delete",
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun WordActionsPreview() {
    MemorifyTheme {
        WordActions(onDeleteClick = {}, onEditClick = {})
    }
}