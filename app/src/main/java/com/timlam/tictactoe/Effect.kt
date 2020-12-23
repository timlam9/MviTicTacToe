package com.timlam.tictactoe

import com.timlam.domain.models.Player

sealed class Effect {

    data class ShowPlayerWinsMessage(val player: Player) : Effect()

    object ShowAlreadyMarkedMessage : Effect()

    object ShowTieMessage : Effect()

}
