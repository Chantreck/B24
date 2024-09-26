package ru.chantreck.brics2024.tv_circles

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.chantreck.brics2024.ui.theme.BRICS2024Theme

@Composable
fun TvContent(
    modifier: Modifier,
) {
    val viewModel: TvViewModel = viewModel()
    val state by viewModel.screenState.collectAsState()

    BoxWithConstraints(
        contentAlignment = Alignment.BottomStart,
        modifier = modifier.fillMaxSize()
    ) {
        val width = this.maxWidth
        val height = this.maxHeight
        val totalPoints = state.players.sumOf { it }
        val maxPoints = state.players.maxOf { it }
        val onePoint = minOf(width / totalPoints, height / maxPoints)

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
            state.players.forEach { player ->
                Box(
                    modifier = Modifier
                        .background(Color.Cyan, CircleShape)
                        .size(onePoint * player)
                )
            }
        }
    }
}
