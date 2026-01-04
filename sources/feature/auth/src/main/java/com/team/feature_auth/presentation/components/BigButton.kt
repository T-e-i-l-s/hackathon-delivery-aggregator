package com.team.feature_auth.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.team.uikit.presentation.buttons.ProdUiButton

@Composable
fun BigButton(
    onClick: () -> Unit,
    text: String,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    ProdUiButton(
        text = text,
        onCLick = onClick,
        isEnabled = enabled,
        isLoading = isLoading,
        modifier = Modifier.fillMaxWidth()
    )
}
