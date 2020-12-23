package com.timlam.tictactoe

import com.timlam.domain.Board
import com.timlam.domain.models.GameStatus
import com.timlam.domain.models.Player

data class TicTacToeState(
    val board: Board = Board(),
    val player: Player = Player.X,
    val gameStatus: GameStatus = GameStatus.Playing
) {

    fun nextPlayer(): Player = if (player == Player.X) Player.O else Player.X

}

