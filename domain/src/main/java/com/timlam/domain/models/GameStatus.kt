package com.timlam.domain.models

sealed class GameStatus {

    object Playing : GameStatus()

    sealed class GameOver : GameStatus() {

        data class PlayerWon(val player: Player) : GameOver()

        object Tie : GameOver()

    }

}
