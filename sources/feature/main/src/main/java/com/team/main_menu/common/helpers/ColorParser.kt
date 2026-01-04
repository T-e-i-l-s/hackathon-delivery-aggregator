package com.team.main_menu.common.helpers

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

fun parseColorOrDefault(
    colorValue: String,
    fallback: Color
): Color {
    return runCatching { Color(colorValue.toColorInt()) }.getOrElse { fallback }
}