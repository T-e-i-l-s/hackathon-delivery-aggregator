package com.team.uikit.presentation.ext

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.baseShadow(
    elevation: Int = 32,
    shape: Int = 32
): Modifier {
    return this.shadow(
        elevation = elevation.dp,
        shape = RoundedCornerShape(shape.dp),
        clip = false,
        ambientColor = MaterialTheme.colorScheme.onBackground,
        spotColor = MaterialTheme.colorScheme.onBackground
    )
}