package com.timlam.tictactoe

import com.timlam.domain.models.GameStatus
import com.timlam.domain.models.Player
import com.timlam.domain.models.Spot

data class TicTacToeState(
    val spots: List<Spot>,
    val player: Player = Player.X,
    val gameStatus: GameStatus = GameStatus.Playing,
    val restart: Boolean = false
) {

    fun nextPlayer(): Player = if (player == Player.X) Player.O else Player.X

}

