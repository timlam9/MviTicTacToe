package com.timlam.tictactoe

sealed class Effect {

    data class ShowPlayerWinsMessage(val player: Player) : Effect()

    object ShowAlreadyMarkedMessage : Effect()

    object ShowTieMessage : Effect()

}
