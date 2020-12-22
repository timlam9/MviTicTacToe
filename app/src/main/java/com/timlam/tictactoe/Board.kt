package com.timlam.tictactoe

data class Board(val spots: List<Spot> = generateBoard()) {

    fun isSpotAvailable(position: Position): Boolean = spots.any { it.position == position && it.mark.isEmpty() }

    fun markSpot(position: Position, mark: String): Board {
        val newSpots = spots.filterNot { it.position == position } as MutableList<Spot>
        newSpots.add(Spot(position, mark))

        return Board(newSpots)
    }

    fun markOfSpot(position: Position): String = spots.first { it.position == position }.mark

}

private fun generateBoard(): List<Spot> {
    return listOf(
        Spot(Position.TOP_LEFT, ""),
        Spot(Position.TOP_CENTER, "")
    )
}
