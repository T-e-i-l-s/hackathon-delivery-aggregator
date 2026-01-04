package com.team.uikit.presentation.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.team.uikit.IS_TAIGA
import com.team.uikit.presentation.text.ProdUiText

/**
 * # ProdUiButton
 *
 * A standard customizable button component with optional icon and loading state.
 * Displays a progress indicator when `isLoading` is true and supports disabled styling.
 *
 * @param text The text label displayed on the button.
 * @param modifier Optional [Modifier] for layout and styling customization.
 * @param containerColor The background color of the button.
 * @param contentColor The color used for the text and icon.
 * @param iconPainter Optional [Painter] for displaying an icon before the text.
 * @param isEnabled Controls whether the button is interactive.
 * @param isLoading If true, replaces the button content with a loading indicator.
 * @param onCLick Callback triggered when the button is clicked.
 */
@Composable
fun ProdUiButton(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    iconPainter: Painter? = null,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onCLick: () -> Unit
) {
    Button(
        onClick = onCLick,
        modifier = modifier,
        contentPadding = PaddingValues(if (IS_TAIGA) 18.dp else 16.dp),
        shape = RoundedCornerShape(if (IS_TAIGA) 16.dp else 20.dp),
        enabled = isEnabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = containerColor.copy(alpha = 0.4f)
        ),
    ) {
        if (!isLoading) {
            if (iconPainter != null) {
                Image(
                    painter = iconPainter,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))
            }

            ProdUiText(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(
                    if (isEnabled) 1f else 0.4f
                )
            )
        } else {
            CircularProgressIndicator(
                color = contentColor,
                trackColor = Color.Transparent,
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp
            )
        }
    }
}