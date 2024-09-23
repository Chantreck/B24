package ru.chantreck.brics2024.clicker

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.chantreck.brics2024.common.CollectAction

@Composable
fun ClickerGameScreen(
    viewModel: ClickerGameViewModel,
    onNetworkError: () -> Unit,
) {
    val state by viewModel.screenState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.init()
    }

    val context = LocalContext.current
    viewModel.action.CollectAction {
        when (it) {
            is ClickerGameAction.PlaySound -> MediaPlayer.create(context, it.sound).start()
            is ClickerGameAction.NetworkError -> onNetworkError()
        }
    }
}

@Composable
private fun PlayerStats(
    points: Int,
    maxPoints: Int,
    username: String,
    modifier: Modifier = Modifier,
    isCurrent: Boolean,
) {
    val fraction = (points.toFloat()) / maxPoints

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        BoxWithConstraints(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            val height = this.maxHeight
            Box(
                modifier = Modifier
                    .background(if (isCurrent) Magenta else Cyan)
                    .height(height * fraction)
                    .width(32.dp),
            )
            Text(text = points.toString(), modifier = Modifier.align(Alignment.TopCenter))
        }
        Text(text = username, fontSize = 12.sp)
    }
}