package com.timlam.tictactoe.presentation

import com.timlam.tictactoe.domain.GameStatus
import com.timlam.tictactoe.domain.Player
import com.timlam.tictactoe.domain.Spot

data class TicTacToeState(
    val spots: List<Spot>,
    val player: Player = Player.X,
    val gameStatus: GameStatus = GameStatus.Playing,
    val restart: Boolean = false
) {

    fun nextPlayer(): Player = if (player == Player.X) Player.O else Player.X

}

