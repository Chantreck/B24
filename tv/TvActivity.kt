package ru.chantreck.brics2024.tv

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.Text
import ru.chantreck.brics2024.ui.theme.BRICS2024Theme

@Composable
fun TvContent(
    modifier: Modifier,
) {
    val viewModel: TvViewModel = viewModel()
    val state by viewModel.screenState.collectAsState()

    val focusRequester = remember { FocusRequester() }
}

@Composable
private fun Film(
    focusRequester: FocusRequester,
    film: FilmState,
) {
    var width by remember { mutableStateOf(100.dp) }
    var height by remember { mutableStateOf(140.dp) }
    var offset by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = film.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    width = if (it.isFocused) 120.dp else 100.dp
                    height = if (it.isFocused) 160.dp else 140.dp
                    offset = if (it.isFocused) -10 else 0
                }
                .focusable()
                .width(width)
                .height(height)
                .offset { IntOffset(x = offset, y = offset) }
                .clip(RoundedCornerShape(4.dp)),
        )
    }
}
