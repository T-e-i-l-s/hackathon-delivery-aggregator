package com.team.uikit.presentation.text

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

/**
 * # ProdUiText
 *
 * Wraps [Text] and animates content changes using [AnimatedContent]
 * if [textAnimation] is provided. Otherwise, shows plain text.
 *
 * @param text Text to display.
 * @param color Text color (default from [MaterialTheme.colorScheme.onBackground]).
 * @param style Text style (default from [MaterialTheme.typography]).
 * @param modifier Modifier for layout or styling.
 * @param textAlign Alignment of the text inside its container.
 * @param textAnimation Optional animation for text changes. If `null`, no animation is applied.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProdUiText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    textAlign: TextAlign = TextAlign.Start,
    textAnimation: ProdUiTextAnimation? = null,
    maxLines: Int = Int.MAX_VALUE,
) {
    if (textAnimation != null) {
        AnimatedContent(
            targetState = text,
            transitionSpec = textAnimation.animation
        ) { targetText ->
            Text(
                text = targetText,
                color = color,
                style = style,
                modifier = modifier,
                textAlign = textAlign,
                maxLines = maxLines,
                overflow = TextOverflow.Ellipsis,
            )
        }
    } else {
        Text(
            text = text,
            color = color,
            style = style,
            modifier = modifier,
            textAlign = textAlign,
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
        )
    }
}