package ru.chantreck.brics2024.memos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MemosScreen() {
    val gridState = rememberLazyGridState()
    var startItem by remember { mutableStateOf<Int?>(null) }
    var lastItem by remember { mutableStateOf<Int?>(null) }
    var startOffset by remember { mutableStateOf(Offset(x = 0f, y = 0f)) }
    var deltaOffset by remember { mutableStateOf(Offset(x = 0f, y = 0f)) }
    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(state.size.columnSize),
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGesturesAfterLongPress(
                    onDrag = { _, offset ->
                        deltaOffset += offset
                    },
                    onDragStart = { offset ->
                        val start = gridState.getItem(offset)
                        startItem = start?.index
                        startOffset = offset
                    },
                    onDragEnd = {
                        val totalOffset = startOffset + deltaOffset
                        lastItem = gridState.getItem(totalOffset)?.index

                        val from = startItem
                        val to = lastItem
                        if (from != null && to != null) {
                            viewModel.changePositionOfItems(from = from, to = to)
                        }

                        startItem = null
                        lastItem = null
                        startOffset = Offset(x = 0f, y = 0f)
                        deltaOffset = Offset(x = 0f, y = 0f)
                    },
                    onDragCancel = { },
                )
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
}

private fun LazyGridState.getItem(offset: Offset): LazyGridItemInfo? {
    return layoutInfo.visibleItemsInfo.find { item ->
        offset.x.toInt() in item.offset.x..item.offsetXEnd() &&
                offset.y.toInt() in item.offset.y..item.offsetYEnd()
    }
}

private fun LazyGridItemInfo.offsetXEnd(): Int {
    return this.offset.x + this.size.width
}

private fun LazyGridItemInfo.offsetYEnd(): Int {
    return this.offset.y + this.size.height
}
