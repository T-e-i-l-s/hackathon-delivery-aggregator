package com.team.uikit.presentation.text

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith

/**
 * # ProdUiTextAnimation
 *
 * Sealed class representing text change animations for [ProdUiText].
 * Provides different animation strategies for entering and exiting text content.
 */
sealed class ProdUiTextAnimation(
    val animation: AnimatedContentTransitionScope<String>.() -> ContentTransform
) {
    /**
     * Vertical animation for text changes.
     * New text slides in from the bottom while fading in, and old text slides out upwards while fading out.
     */
    class Vertical : ProdUiTextAnimation(
        {
            (slideInVertically { it } + fadeIn())
                .togetherWith(slideOutVertically { -it } + fadeOut())
        }
    )

    /**
     * Horizontal animation for text changes.
     * New text slides in from the right while fading in, and old text slides out to the left while fading out.
     */
    class Horizontal : ProdUiTextAnimation(
        {
            (slideInHorizontally { it } + fadeIn())
                .togetherWith(slideOutHorizontally { -it } + fadeOut())
        }
    )
}