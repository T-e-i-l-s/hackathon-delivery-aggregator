package com.team.uikit.presentation.inputs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * # ProdUiSlider
 *
 * A custom-styled slider component that matches the app’s color scheme.
 * Allows users to select a value from a continuous range.
 *
 * @param value The current value of the slider.
 * @param onValueChange Callback triggered when the slider value changes.
 * @param modifier Optional [Modifier] for layout and styling customization.
 */
@Composable
fun ProdUiSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.primary,
            activeTrackColor = MaterialTheme.colorScheme.primary,
            inactiveTrackColor = MaterialTheme.colorScheme.tertiary,
        ),
        modifier = modifier
    )
}