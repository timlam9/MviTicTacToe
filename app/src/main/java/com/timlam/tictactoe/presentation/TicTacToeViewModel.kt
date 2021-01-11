package com.timlam.tictactoe.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timlam.tictactoe.domain.Board
import com.timlam.tictactoe.domain.GameEngine
import com.timlam.tictactoe.domain.GameStatus
import com.timlam.tictactoe.domain.Player
import com.timlam.tictactoe.domain.Position
import kotlinx.coroutines.launch

class TicTacToeViewModel(
    private val gameEngine: GameEngine = GameEngine(),
    private val board: Board = Board(),
    private val ticTacToe: TicTacToe = TicTacToe(TicTacToeState(board.spots))
) : ViewModel() {

    val state = ticTacToe.state
    val effects = ticTacToe.effects

    fun onEvent(event: Event) {
        viewModelScope.launch {
            reduce(state.value, event).apply {
                ticTacToe.setState(this)
            }
        }
    }

    private suspend fun reduce(currentState: TicTacToeState, event: Event): TicTacToeState {
        return when (event) {
            is Event.OnSpotClicked -> handleSpotClicked(currentState, event.position)
            is Event.OnRestartClicked -> updateState(currentState, board.apply { reset() }, GameStatus.Playing, Player.X)
        }
    }

    private suspend fun handleSpotClicked(currentState: TicTacToeState, position: Position): TicTacToeState {
        return try {
            board.captureSpot(position, currentState.player)
            gameEngine.updateStatus(board, currentState.player).let { gameStatus ->
                when (gameStatus) {
                    is GameStatus.GameOver.PlayerWon -> ticTacToe.emit(Effect.ShowPlayerWinsMessage(player = currentState.player))
                    is GameStatus.GameOver.Tie -> ticTacToe.emit(Effect.ShowTieMessage)
                    else -> {
                        // Continue playing without any effects
                    }
                }
                updateState(currentState, board, gameStatus)
            }
        } catch (e: Board.SpotAlreadyMarkedException) {
            ticTacToe.emit(Effect.ShowAlreadyMarkedMessage)
            currentState
        } catch (e: Board.GameOverException) {
            ticTacToe.emit(Effect.ShowGameOverMessageMessage)
            currentState
        }
    }

    private fun updateState(
        currentState: TicTacToeState,
        board: Board,
        gameStatus: GameStatus,
        player: Player = currentState.nextPlayer()
    ) = currentState.copy(
        spots = board.spots,
        player = player,
        gameStatus = gameStatus,
        restart = gameStatus is GameStatus.GameOver
    )

}
