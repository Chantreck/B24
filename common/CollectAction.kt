package ru.chantreck.brics2024.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow

@Suppress("ComposableEventParameterNaming")
@Composable
fun <Action : ScreenAction> Flow<Action>.CollectAction(actionHandler: (Action) -> Unit) {
    LaunchedEffect(key1 = Unit) {
        this@CollectAction.collect {
            actionHandler(it)
        }
    }
}
