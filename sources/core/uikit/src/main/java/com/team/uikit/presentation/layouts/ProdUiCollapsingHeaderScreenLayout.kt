package com.team.uikit.presentation.layouts

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * # ProdUiCollapsingHeaderScreenLayout
 *
 * Layout with a top header that collapses as the user scrolls.
 *
 * - [header] — fixed at the top, collapses when content scrolls upward.
 * - [content] — main scrollable area positioned under the header.
 */
@Composable
fun ProdUiCollapsingHeaderScreenLayout(
    header: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
    modifier: Modifier
) {
    val contentScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var animationJob by remember { mutableStateOf<Job?>(null) }


    var headerHeightPx by remember { mutableIntStateOf(0) }
    var previousScroll by remember { mutableIntStateOf(0) }

    LaunchedEffect(contentScrollState.value) {
        val current = contentScrollState.value
        if (current > previousScroll && current < headerHeightPx) {
            animationJob?.cancel()
            animationJob = coroutineScope.launch {
                contentScrollState.animateScrollTo(headerHeightPx, tween(40))
            }
        } else if (current < previousScroll && current < headerHeightPx) {
            animationJob?.cancel()
            animationJob = coroutineScope.launch {
                contentScrollState.animateScrollTo(0, tween(40))
            }
        }
        previousScroll = current
    }

    Box(modifier) {
        Column(
            Modifier
                .onGloballyPositioned { coords ->
                    headerHeightPx = coords.size.height
                }
        ) {
            header()
        }

        Column(
            Modifier
                .statusBarsPadding()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .verticalScroll(contentScrollState)
        ) {
            Spacer(Modifier.height(with(LocalDensity.current) { headerHeightPx.toDp() }))
            content()
        }
    }
}