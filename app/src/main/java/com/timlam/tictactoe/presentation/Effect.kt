package com.timlam.tictactoe.presentation

import com.timlam.tictactoe.domain.Player

sealed class Effect {

    data class ShowPlayerWinsMessage(val player: Player) : Effect()

    object ShowAlreadyMarkedMessage : Effect()

    object ShowTieMessage : Effect()

    object ShowGameOverMessageMessage : Effect()

}
