package com.team.uikit.presentation.inputs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.team.uikit.R
import com.team.uikit.presentation.text.ProdUiText

/**
 * # ProdUiStepper
 *
 * A counter component that allows users to increment or decrement a numeric value
 * within a defined range using plus and minus buttons.
 *
 * @param currentStep The current numeric value displayed in the stepper.
 * @param onStepChange Callback triggered when the value changes (increment or decrement).
 * @param minStep The minimum allowed value for the counter (default is 0).
 * @param maxStep The maximum allowed value for the counter (default is 10).
 */
@Composable
fun ProdUiStepper(
    currentStep: Int,
    onStepChange: (Int) -> Unit,
    minStep: Int = 0,
    maxStep: Int = 10
) {
    Row(
        Modifier
            .border(
                1.dp,
                MaterialTheme.colorScheme.tertiary,
                RoundedCornerShape(50)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { onStepChange(currentStep - 1) },
            enabled = currentStep > minStep
        ) {
            Icon(
                painter = painterResource(id = R.drawable.minus_icon),
                contentDescription = null,
                tint = if (currentStep > minStep) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.tertiary
                },
                modifier = Modifier.size(24.dp)
            )
        }

        ProdUiText(
            text = currentStep.toString(),
            style = MaterialTheme.typography.titleMedium
        )

        IconButton(
            onClick = { onStepChange(currentStep + 1) },
            enabled = currentStep < maxStep
        ) {
            Icon(
                painter = painterResource(id = R.drawable.plus_icon),
                contentDescription = null,
                tint = if (currentStep < maxStep) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                modifier = Modifier.size(24.dp)
            )
        }
    }
}