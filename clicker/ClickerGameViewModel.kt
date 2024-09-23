package ru.chantreck.brics2024.clicker

import androidx.lifecycle.viewModelScope
import com.example.brics2024.R
import io.github.jan.supabase.annotations.SupabaseExperimental
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.chantreck.brics2024.SharedPreferencesProvider
import ru.chantreck.brics2024.SupabaseClient
import ru.chantreck.brics2024.clicker.widget.ClickerObservable
import ru.chantreck.brics2024.common.BaseViewModel

class ClickerGameViewModel : BaseViewModel<ClickerGameState, ClickerGameAction>() {

    private var currentUserId: String = ""

    override fun createInitState(): ClickerGameState {
        return ClickerGameState(
            isLoading = true,
            players = emptyList(),
        )
    }

    @OptIn(SupabaseExperimental::class)
    fun init() {
        currentUserId = SharedPreferencesProvider.getUserId() ?: return

        viewModelScope.launch {
            try {
                SupabaseClient.client.from("users")
                    .selectAsFlow(PlayerStats::id)
                    .map { players ->
                        players.map { if (it.id == currentUserId) it.copy(isCurrent = true) else it }
                    }.collect { players ->
                        val sorted = players.sortedBy { it.username }
                        _screenState.value = currentState.copy(players = sorted, isLoading = false)

                        val currentPlayerStats =
                            currentState.players.find { it.id == currentUserId } ?: return@collect
                        ClickerObservable.notifyObservers(currentPlayerStats.points)
                    }
            } catch (e: Exception) {
                sendAction(ClickerGameAction.NetworkError)
            }
        }
    }

    fun click() {
        val currentPlayerStats = currentState.players.find { it.id == currentUserId } ?: return
        val newValue = currentPlayerStats.points + 1
        if (newValue == 20) {
            sendAction(ClickerGameAction.PlaySound(R.raw.win))
        }

        viewModelScope.launch {
            SupabaseClient.client.from("users").update(
                {
                    PlayerStats::points setTo newValue
                }
            ) {
                filter {
                    PlayerStats::id eq currentUserId
                }
            }
        }
    }
}
