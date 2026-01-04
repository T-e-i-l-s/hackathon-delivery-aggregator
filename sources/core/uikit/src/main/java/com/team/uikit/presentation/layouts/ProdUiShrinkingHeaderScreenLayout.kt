package com.team.uikit.presentation.layouts

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.zIndex

/**
 * # ProdUiShrinkingHeaderScreenLayout
 *
 * Layout with a top header that shrinks on scroll.
 *
 * - [header] — always visible, changes size based on scroll.
 * - [content] — main scrollable area, enabled after the header shrinks.
 */
@Composable
fun ProdUiShrinkingHeaderScreenLayout(
    header: @Composable (shrinked: Boolean) -> Unit,
    content: @Composable (lazyListState: LazyListState, headerHeightPx: Int, enabled: Boolean) -> Unit,
    modifier: Modifier
) {
    val lazyListState = rememberLazyListState()

    var shrinked by remember {
        mutableStateOf(
            lazyListState.firstVisibleItemIndex != 0 &&
                    lazyListState.firstVisibleItemScrollOffset != 0
        )
    }
    var headerHeightPx by remember { mutableIntStateOf(0) }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val atTop = lazyListState.firstVisibleItemIndex == 0 &&
                        lazyListState.firstVisibleItemScrollOffset == 0

                if (available.y > 20 && atTop) {
                    shrinked = false
                }

                return Offset.Zero
            }
        }
    }

    Box(
        modifier
            .pointerInput(Unit) {
                detectVerticalDragGestures { change, dragAmount ->
                    if (dragAmount < 20) {
                        if (!shrinked) {
                            shrinked = true
                        }
                    }
                }
            }
            .nestedScroll(nestedScrollConnection)
    ) {
        Column(
            Modifier
                .zIndex(1f)
                .onGloballyPositioned { coords ->
                    headerHeightPx = coords.size.height
                }
        ) {
            header(shrinked)
        }

        content(lazyListState, headerHeightPx, shrinked)
    }
}