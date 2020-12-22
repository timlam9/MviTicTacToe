package com.timlam.tictactoe

sealed class Event {

    data class OnSpotClicked(val position: Position) : Event()

}
