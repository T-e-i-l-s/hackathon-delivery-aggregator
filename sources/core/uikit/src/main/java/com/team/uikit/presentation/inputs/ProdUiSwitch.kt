package com.team.uikit.presentation.inputs

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * # ProdUiSwitch
 *
 * A custom-styled switch component that reflects the app’s color scheme.
 * Used to toggle between two states, such as on/off or enabled/disabled.
 *
 * @param checked Indicates whether the switch is currently in the "on" position.
 * @param onCheckedChange Callback triggered when the switch state changes.
 */
@Composable
fun ProdUiSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedTrackColor = MaterialTheme.colorScheme.primary,
            checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
            checkedBorderColor = Color.Transparent,
            uncheckedTrackColor = MaterialTheme.colorScheme.tertiary,
            uncheckedThumbColor = MaterialTheme.colorScheme.onPrimary,
            uncheckedBorderColor = Color.Transparent
        )
    )
}