package ru.chantreck.brics2024.memos

import androidx.annotation.DrawableRes
import ru.chantreck.brics2024.common.ScreenState

data class MemosState(
    val isLoading: Boolean = true,
    val isPaused: Boolean = false,
    val size: Size = Size.FOUR_FOUR,
    val items: List<MemosItem> = emptyList(),
) : ScreenState

enum class Size(val pairs: Int, val text: String, val columnSize: Int) {

    FOUR_FOUR(8, "4x4", 4),
    FIVE_FIVE(13, "5x5", 5),
    SIX_SIX(18, "6x6", 6),
}

data class MemosItem(
    val id: Int,
    @DrawableRes val drawable: Int,
    val isOpened: Boolean,
    val code: Int,
)
