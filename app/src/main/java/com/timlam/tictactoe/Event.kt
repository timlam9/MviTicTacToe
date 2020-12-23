package com.timlam.tictactoe

import com.timlam.domain.models.Position

sealed class Event {

    data class OnSpotClicked(val position: Position) : Event()

}
