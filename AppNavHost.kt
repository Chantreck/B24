package ru.chantreck.brics2024

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import ru.chantreck.brics2024.clicker.ClickerGameScreen
import ru.chantreck.brics2024.dialog.NetworkErrorDialog
import ru.chantreck.brics2024.draw_image.DrawImageScreen
import ru.chantreck.brics2024.games.GameListScreen
import ru.chantreck.brics2024.leadership.LeadershipScreen
import ru.chantreck.brics2024.select_player.SelectPlayerScreen
import ru.chantreck.brics2024.ui.components.BottomBar

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeRoute,
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        dialog<NetworkErrorDialogRoute> {
            NetworkErrorDialog()
        }

        composable<HomeRoute> {
            val bottomNavController = rememberNavController()

            Scaffold(
                modifier = modifier.fillMaxSize(),
                bottomBar = {
                    BottomBar(navController = bottomNavController)
                },
                containerColor = Color.Gray,
                contentWindowInsets = WindowInsets(
                    left = 0.dp,
                    top = 0.dp,
                    right = 0.dp,
                    bottom = 0.dp
                ),
            ) { innerPadding ->

                NavHost(
                    navController = bottomNavController,
                    startDestination = GameListDestination,
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding()
                ) {

                    composable<SelectPlayerDestination> {
                        SelectPlayerScreen(
                            viewModel = viewModel(),
                            onPlayerSelected = {
                                bottomNavController.navigate(route = GameListDestination)
                            },
                            onNetworkError = {
                                navController.navigate(NetworkErrorDialogRoute)
                            }
                        )
                    }
                }
            }
        }
    }
}
