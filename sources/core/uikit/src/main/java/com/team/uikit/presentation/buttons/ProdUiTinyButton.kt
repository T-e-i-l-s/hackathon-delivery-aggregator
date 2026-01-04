package com.team.uikit.presentation.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.team.uikit.presentation.text.ProdUiText

/**
 * # ProdUiTinyButton
 *
 * A compact version of the standard button, designed for secondary or space-constrained actions.
 * Supports an optional icon and disabled state.
 *
 * @param text The text label displayed on the button.
 * @param modifier Optional [Modifier] for layout and styling customization.
 * @param containerColor The background color of the button.
 * @param contentColor The color used for the text and icon.
 * @param iconPainter Optional [Painter] for displaying an icon before the text.
 * @param isEnabled Controls whether the button is interactive.
 * @param onCLick Callback triggered when the button is clicked.
 */
@Composable
fun ProdUiTinyButton(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    iconPainter: Painter? = null,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(16.dp),
        enabled = isEnabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.4f)
        )
    ) {
        if (iconPainter != null) {
            Icon(
                painter = iconPainter,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(16.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
        }

        ProdUiText(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor.copy(
                if (isEnabled) 1f else 0.4f
            )
        )
    }
}