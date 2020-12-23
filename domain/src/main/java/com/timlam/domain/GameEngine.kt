package com.timlam.domain

import com.timlam.domain.models.GameStatus
import com.timlam.domain.models.Player

class GameEngine {

    fun updateStatus(board: Board, player: Player): GameStatus = when {
        board.isWon(player) -> GameStatus.GameOver.PlayerWon(player)
        board.isFull() -> GameStatus.GameOver.Tie
        else -> GameStatus.Playing
    }

}
