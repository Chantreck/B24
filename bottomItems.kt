package ru.chantreck.brics2024.ui.components.bottom

import ru.chantreck.brics2024.GameListDestination
import ru.chantreck.brics2024.LeadershipDestination
import ru.chantreck.brics2024.SelectPlayerDestination

val bottomItems = listOf(
    BottomItem.Home,
    BottomItem.DailyPractice,
    BottomItem.Play,
    BottomItem.Player,
    BottomItem.Leadership,
)

sealed class BottomItem(
    val route: Any,
    val icon: Int,
) {

    data object Home : BottomItem(
        route = "Main",
        icon = android.R.drawable.ic_lock_lock,
    )

    data object DailyPractice : BottomItem(
        route = "Daily Practice",
        icon = android.R.drawable.ic_delete,
    )

    data object Play : BottomItem(
        route = GameListDestination,
        icon = android.R.drawable.ic_media_play,
    )

    data object Player : BottomItem(
        route = SelectPlayerDestination,
        icon = android.R.drawable.ic_menu_call,
    )

    data object Leadership : BottomItem(
        route = LeadershipDestination,
        icon = android.R.drawable.ic_lock_idle_alarm,
    )
}