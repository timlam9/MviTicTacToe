package com.timlam.tictactoe.domain

import java.lang.Exception

class Board {

    object SpotAlreadyMarkedException : Exception()
    object GameOverException : Exception()

    private var _spots = generateBoard()
    val spots: List<Spot>
        get() = _spots

    fun captureSpot(position: Position, player: Player) {
        if (isGameOver()) throw GameOverException
        if (!isSpotAvailable(position)) throw SpotAlreadyMarkedException
        _spots = _spots.filterNot { it.position == position } as MutableList<Spot>
        _spots.add(Spot(position, player.name))
    }

    private fun isGameOver() = isFull() || isWon(Player.X) || isWon(Player.O)

    fun isWon(player: Player): Boolean {
        val currentMoves = currentPlayerMoves(player)
        return winningCombinations.any { winningSet: Set<Position> ->
            currentMoves.containsAll(winningSet)
        }
    }

    private fun currentPlayerMoves(player: Player) = _spots.filter { spot ->
        spot.mark == player.name
    }.map { spot ->
        spot.position
    }.toSet()


    fun isFull(): Boolean = _spots.all { it.mark.isNotEmpty() }

    private fun isSpotAvailable(position: Position): Boolean = _spots.any { it.position == position && it.mark.isEmpty() }

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

    private fun generateBoard(): MutableList<Spot> {
        return mutableListOf(
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

    fun reset() {
        _spots = generateBoard()
    }

}


