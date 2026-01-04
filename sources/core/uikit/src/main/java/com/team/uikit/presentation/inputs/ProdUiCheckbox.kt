package com.team.uikit.presentation.inputs

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * # ProdUiCheckbox
 *
 * A custom-styled checkbox component that matches the app’s color scheme.
 * Used to toggle a binary option on or off.
 *
 * @param checked Indicates whether the checkbox is currently selected.
 * @param onCheckedChange Callback triggered when the checkbox state changes.
 */
@Composable
fun ProdUiCheckbox(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(
            checkedColor = MaterialTheme.colorScheme.primary,
            checkmarkColor = MaterialTheme.colorScheme.onPrimary,
            uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    )
}