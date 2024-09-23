package ru.chantreck.brics2024.common

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Stable
@Suppress("VariableNaming")
abstract class BaseViewModel<State : ScreenState, Action : ScreenAction> : ViewModel() {

    protected val _screenState by lazy { MutableStateFlow(createInitState()) }
    protected val currentState: State get() = screenState.value
    val screenState: StateFlow<State> = _screenState.asStateFlow()

    private val _action = Channel<Action>()
    val action = _action.receiveAsFlow()

    protected abstract fun createInitState(): State

    protected fun sendAction(action: Action) {
        viewModelScope.launch {
            _action.send(action)
        }
    }
}
