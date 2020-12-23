package com.timlam.tictactoe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timlam.domain.Board
import com.timlam.domain.GameEngine
import com.timlam.domain.models.GameStatus
import com.timlam.domain.models.Position
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TicTacToeViewModel(private val gameEngine: GameEngine = GameEngine()) : ViewModel() {

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
            is Event.OnSpotClicked -> handleSpotClicked(currentState, event.position)
        }
    }

    private suspend fun handleSpotClicked(currentState: TicTacToeState, position: Position): TicTacToeState {
        return if (currentState.board.isSpotAvailable(position)) {
            val board = currentState.board.markSpot(position, currentState.player.name)
            val gameStatus = gameEngine.updateStatus(board, currentState.player).also {
                when (it) {
                    is GameStatus.GameOver.PlayerWon -> _effects.emit(Effect.ShowPlayerWinsMessage(player = currentState.player))
                    is GameStatus.GameOver.Tie -> _effects.emit(Effect.ShowTieMessage)
                    else -> {
                    } // Continue playing without any effects
                }
            }
            updateState(currentState, board, gameStatus)
        } else {
            _effects.emit(Effect.ShowAlreadyMarkedMessage)
            currentState
        }
    }

    private fun updateState(
        currentState: TicTacToeState,
        board: Board,
        gameStatus: GameStatus
    ) = currentState.copy(
        board = board,
        player = currentState.nextPlayer(),
        gameStatus = gameStatus
    )

}
