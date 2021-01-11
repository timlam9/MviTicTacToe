package com.timlam.tictactoe.domain

class GameEngine {

    fun updateStatus(board: Board, player: Player): GameStatus = when {
        board.isWon(player) -> GameStatus.GameOver.PlayerWon(player)
        board.isFull() -> GameStatus.GameOver.Tie
        else -> GameStatus.Playing
    }

}
