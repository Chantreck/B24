package ru.chantreck.brics2024.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.chantreck.brics2024.GameListDestination
import ru.chantreck.brics2024.ui.components.bottom.BottomItem
import ru.chantreck.brics2024.ui.components.bottom.bottomItems

@Composable
fun BottomBar(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Row(
        modifier = modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .height(40.dp)
            .drawBehind {
                drawRect(color = Color.Magenta)
            }
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.Bottom,
    ) {
        bottomItems.forEach { item ->
            if (item is BottomItem.Play) {
                PlayItem(
                    item = item,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            navController.bottomNavigate(item)
                        },
                )
            } else {
                NavBarItem(
                    iconRes = item.icon,
                    isCurrent = isCurrentDestination(
                        currentDestination = currentDestination,
                        item.route,
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            navController.bottomNavigate(item)
                        },
                )
            }
        }
    }
}

@Composable
private fun NavBarItem(
    @DrawableRes iconRes: Int,
    isCurrent: Boolean,
    modifier: Modifier = Modifier,
) {
    Image(
        bitmap = ImageBitmap.imageResource(iconRes),
        contentDescription = null,
        modifier = modifier.size(24.dp),
        colorFilter = ColorFilter.tint(if (isCurrent) Color.DarkGray else Color.White)
    )
}

@Composable
private fun PlayItem(
    item: BottomItem,
    modifier: Modifier = Modifier,
) {
    Icon(
        bitmap = ImageBitmap.imageResource(item.icon),
        contentDescription = null,
        modifier = modifier
            .size(24.dp)
            .offset(y = (-20).dp)
            .drawBehind {
                drawArc(
                    color = Color.Gray,
                    startAngle = 0f,
                    sweepAngle = 180f,
                    size = Size(72.dp.toPx(), 72.dp.toPx()),
                    topLeft = Offset(
                        x = this.center.x - 36.dp.toPx(),
                        y = this.center.y - 36.dp.toPx()
                    ),
                    useCenter = true
                )
                drawCircle(color = Color.Magenta, radius = 28.dp.toPx())
            },
        tint = Color.White,
    )
}

private fun isCurrentDestination(currentDestination: NavDestination?, route: Any): Boolean {
    return currentDestination?.hierarchy?.any { it == route } == true
}

private fun NavController.bottomNavigate(bottomItem: BottomItem) {
    this.navigate(bottomItem.route) {
        popUpTo(GameListDestination) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
