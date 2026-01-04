package com.team.uikit.presentation.modals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.team.uikit.presentation.buttons.ProdUiButton
import com.team.uikit.presentation.text.ProdUiText

/**
 * # ProdUiWarningModal
 *
 * A simple alert dialog displaying a title, a message, and a single action button.
 * Typically used to inform the user about a warning or important message that requires acknowledgment.
 *
 * @param title The title text displayed at the top of the alert.
 * @param text The main message text shown below the title.
 * @param buttonText The label for the single confirmation or acknowledgment button.
 * @param onClose Callback triggered when the dialog is dismissed or the button is clicked.
 */
@Composable
fun ProdUiWarningModal(
    title: String,
    text: String,
    buttonText: String,
    onClose: () -> Unit,
) {
    Dialog(
        onDismissRequest = onClose,
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
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            ProdUiButton(
                text = buttonText,
                modifier = Modifier.fillMaxWidth(),
                onCLick = onClose
            )
        }
    }
}