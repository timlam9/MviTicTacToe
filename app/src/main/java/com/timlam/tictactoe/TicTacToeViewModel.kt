package com.timlam.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TicTacToeViewModel : ViewModel() {

    private val _state = MutableStateFlow(TicTacToeState())
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<Effect>(0)
    val effects = _effects.asSharedFlow()

    fun onEvent(event: Event) {
        viewModelScope.launch {
            reduce(state.value, event).apply {
                _state.value = this
            }
        }
    }

    private suspend fun reduce(currentState: TicTacToeState, event: Event): TicTacToeState {
        return when (event) {
            is Event.OnSpotClicked -> handleSpotClicked(currentState, event.spot)
        }
    }

    private suspend fun handleSpotClicked(currentState: TicTacToeState, spot: Spot): TicTacToeState {
        return if (spot == Spot.TOP_LEFT && currentState.topLeftSpot.isEmpty()) {
            currentState.copy(topLeftSpot = "X")
        } else {
            _effects.emit(Effect.ShowAlreadyMarkedMessage)
            currentState
        }
    }

}
