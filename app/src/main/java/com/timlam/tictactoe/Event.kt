package com.timlam.tictactoe

import com.timlam.domain.models.Position

sealed class Event {

    object OnRestartClicked : Event()

    data class OnSpotClicked(val position: Position) : Event()

}
