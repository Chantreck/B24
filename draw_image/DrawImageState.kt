package ru.chantreck.brics2024.draw_image

import android.net.Uri
import ru.chantreck.brics2024.common.ScreenState

data class DrawImageState(
    val isLoading: Boolean,
    val uri: Uri?,
    val result: String? = null,
) : ScreenState
