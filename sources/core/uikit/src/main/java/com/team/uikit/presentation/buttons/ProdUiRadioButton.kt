package com.team.uikit.presentation.buttons

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable

/**
 * # ProdUiRadioButton
 *
 * A custom-styled radio button component that follows the app’s color scheme.
 * Used for selecting a single option from a group.
 *
 * @param selected Indicates whether the radio button is currently selected.
 * @param onClick Callback triggered when the radio button is clicked.
 */
@Composable
fun ProdUiRadioButton(selected: Boolean, onClick: () -> Unit) {
    RadioButton(
        selected = selected,
        onClick = onClick,
        colors = RadioButtonDefaults.colors(
            selectedColor = MaterialTheme.colorScheme.primary,
            unselectedColor = MaterialTheme.colorScheme.tertiary,
        )
    )
}