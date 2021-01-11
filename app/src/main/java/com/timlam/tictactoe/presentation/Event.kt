package com.timlam.tictactoe.presentation

import com.timlam.tictactoe.domain.Position

sealed class Event {

    object OnRestartClicked : Event()

    data class OnSpotClicked(val position: Position) : Event()

}
