package com.timlam.tictactoe

data class TicTacToeState(
    val board: Board = Board(),
    val player: Player = Player.X,
    val gameStatus: GameStatus = GameStatus.Playing
) {

    fun nextPlayer(): Player = if (player == Player.X) Player.O else Player.X

}

