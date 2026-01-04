package com.team.uikit.presentation.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.team.uikit.presentation.text.ProdUiText
import com.team.uikit.presentation.buttons.ProdUiButton

/**
 * # ProdUiConfirmationModal
 *
 * A confirmation dialog with two actions — a primary confirmation button and a secondary dismissal option.
 * Commonly used for actions that require explicit user confirmation (e.g. deleting an item).
 *
 * @param title The title text displayed at the top of the modal.
 * @param text The main message text providing details or context for the confirmation.
 * @param confirmButtonText The label for the primary action button (e.g. "Confirm", "Delete").
 * @param denyButtonText The label for the secondary dismissal action (e.g. "Cancel").
 * @param onConfirm Callback triggered when the confirmation button is clicked.
 * @param onDeny Callback triggered when the modal is dismissed or the deny action is selected.
 */
@Composable
fun ProdUiConfirmationModal(
    title: String,
    text: String,
    confirmButtonText: String,
    denyButtonText: String,
    onConfirm: () -> Unit,
    onDeny: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDeny,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProdUiText(
                text = title,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            ProdUiText(
                text = text,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            ProdUiButton(
                text = confirmButtonText,
                modifier = Modifier.fillMaxWidth(),
                onCLick = onConfirm
            )

            Spacer(modifier = Modifier.height(24.dp))
            
            ProdUiText(
                text = denyButtonText,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onDeny
                ),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}