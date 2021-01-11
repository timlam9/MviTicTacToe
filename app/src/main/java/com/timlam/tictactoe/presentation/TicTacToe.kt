package com.timlam.tictactoe.presentation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

data class TicTacToe(private val initialState: TicTacToeState) {

    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    private val _effects = MutableSharedFlow<Effect>(0)
    val effects = _effects.asSharedFlow()

    fun setState(ticTacToeState: TicTacToeState) {
        _state.value = ticTacToeState
    }

    suspend fun emit(effect: Effect) {
        _effects.emit(effect)
    }

}
