package com.timlam.domain

import com.timlam.domain.models.Player
import com.timlam.domain.models.Position
import com.timlam.domain.models.Spot

data class Board(val spots: List<Spot> = generateBoard()) {

    private val winningCombinations = setOf(
        setOf(
            Position.TOP_LEFT,
            Position.TOP_CENTER,
            Position.TOP_RIGHT
        ),
        setOf(
            Position.MID_LEFT,
            Position.MID_CENTER,
            Position.MID_RIGHT
        ),
        setOf(
            Position.BOTTOM_LEFT,
            Position.BOTTOM_CENTER,
            Position.BOTTOM_RIGHT
        ),
        setOf(
            Position.TOP_LEFT,
            Position.MID_LEFT,
            Position.BOTTOM_LEFT
        ),
        setOf(
            Position.TOP_CENTER,
            Position.MID_CENTER,
            Position.BOTTOM_CENTER
        ),
        setOf(
            Position.TOP_RIGHT,
            Position.MID_RIGHT,
            Position.BOTTOM_RIGHT
        ),
        setOf(
            Position.TOP_LEFT,
            Position.MID_CENTER,
            Position.BOTTOM_RIGHT
        ),
        setOf(
            Position.TOP_RIGHT,
            Position.MID_CENTER,
            Position.BOTTOM_LEFT
        )
    )

    fun isSpotAvailable(position: Position): Boolean = spots.any { it.position == position && it.mark.isEmpty() }

    fun markSpot(position: Position, mark: String): Board {
        val newSpots = spots.filterNot { it.position == position } as MutableList<Spot>
        newSpots.add(Spot(position, mark))

        return Board(newSpots)
    }

    fun markOfSpot(position: Position): String = spots.first { it.position == position }.mark

    fun isWon(player: Player): Boolean =
        winningCombinations.contains(spots.filter { it.mark == player.name }.map { it.position }.toSet())

    fun isGameTie(): Boolean = spots.all { it.mark.isNotEmpty() }


}

private fun generateBoard(): List<Spot> {
    return listOf(
        Spot(Position.TOP_LEFT, ""),
        Spot(Position.TOP_CENTER, ""),
        Spot(Position.TOP_RIGHT, ""),
        Spot(Position.MID_LEFT, ""),
        Spot(Position.MID_CENTER, ""),
        Spot(Position.MID_RIGHT, ""),
        Spot(Position.BOTTOM_LEFT, ""),
        Spot(Position.BOTTOM_CENTER, ""),
        Spot(Position.BOTTOM_RIGHT, "")
    )
}
