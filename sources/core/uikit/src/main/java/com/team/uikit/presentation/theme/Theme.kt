package com.team.uikit.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.team.uikit.IS_TAIGA
import com.team.uikit.presentation.theme.configs.DefaultDarkColors
import com.team.uikit.presentation.theme.configs.DefaultLightColors
import com.team.uikit.presentation.theme.configs.DefaultTypography
import com.team.uikit.presentation.theme.configs.TuiDarkColors
import com.team.uikit.presentation.theme.configs.TuiLightColors
import com.team.uikit.presentation.theme.configs.TuiTypography

@Composable
fun ProdUiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (IS_TAIGA) {
        if (darkTheme) TuiDarkColors else TuiLightColors
    } else {
        if (darkTheme) DefaultDarkColors else DefaultLightColors
    }

    MaterialTheme(
        typography = if (IS_TAIGA) TuiTypography else DefaultTypography,
        colorScheme = colorScheme,
        content = content
    )
}