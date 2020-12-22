package com.timlam.tictactoe

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TicTacToeViewModel : ViewModel() {

    private val _state = MutableStateFlow(TicTacToeState())
    val state = _state.asStateFlow()

    fun onEvent(event: Event) {
        reduce(state.value, event).apply {
            _state.value = this
        }
    }

    private fun reduce(currentState: TicTacToeState, event: Event): TicTacToeState {
        return currentState.copy(topLeftSpot = "X")
    }

}
