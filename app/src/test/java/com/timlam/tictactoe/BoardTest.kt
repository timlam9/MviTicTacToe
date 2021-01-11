package com.timlam.tictactoe

import com.timlam.tictactoe.domain.Board
import com.timlam.tictactoe.domain.Player
import com.timlam.tictactoe.domain.Position
import com.timlam.tictactoe.domain.findMarkOfPosition
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class BoardTest {

    private val board: Board = Board()

    @Test
    fun `when initializing all spots should be empty`() {
        board.spots.forEach {
            assertTrue(it.mark.isEmpty())
        }
    }

    @Test
    fun `when marking a spot, then it should not be available`() {
        board.captureSpot(Position.BOTTOM_CENTER, Player.X)

        assertEquals(Player.X.name, board.spots.findMarkOfPosition(Position.BOTTOM_CENTER))
    }

    @Test
    fun `given a spot is available, when checking for availability, then should be true`() {
        assertTrue(board.spots.findMarkOfPosition(Position.BOTTOM_CENTER).isEmpty())
    }

    @Test
    fun `given a spot is already marked, when marking a spot, then through an already marked exception`() {
        board.captureSpot(Position.BOTTOM_CENTER, Player.X)

        try {
            board.captureSpot(Position.BOTTOM_CENTER, Player.X)
        } catch (exception: Exception) {
            assertEquals(Board.SpotAlreadyMarkedException, exception)
        }
    }

    @Test
    fun `is full returns true, when all spots are marked`() {
        board.captureSpot(Position.TOP_LEFT, Player.X)
        board.captureSpot(Position.MID_LEFT, Player.O)
        board.captureSpot(Position.TOP_CENTER, Player.X)
        board.captureSpot(Position.MID_CENTER, Player.O)
        board.captureSpot(Position.MID_RIGHT, Player.X)
        board.captureSpot(Position.TOP_RIGHT, Player.O)
        board.captureSpot(Position.BOTTOM_LEFT, Player.X)
        board.captureSpot(Position.BOTTOM_CENTER, Player.O)
        board.captureSpot(Position.BOTTOM_RIGHT, Player.X)

        assertTrue(board.isFull())
    }

    @Test
    fun `when player moves match a winning combination, then the board is won`() {
        board.captureSpot(Position.BOTTOM_LEFT, Player.X)
        board.captureSpot(Position.BOTTOM_CENTER, Player.O)
        board.captureSpot(Position.MID_LEFT, Player.X)
        board.captureSpot(Position.TOP_LEFT, Player.O)
        board.captureSpot(Position.MID_CENTER, Player.X)
        board.captureSpot(Position.TOP_CENTER, Player.O)
        board.captureSpot(Position.MID_RIGHT, Player.X)

        assertTrue(board.isWon(Player.X))
    }

    @Test
    fun `when resetting all spots should be empty`() {
        board.captureSpot(Position.BOTTOM_LEFT, Player.X)
        board.captureSpot(Position.BOTTOM_CENTER, Player.O)
        board.captureSpot(Position.MID_LEFT, Player.X)
        board.captureSpot(Position.TOP_LEFT, Player.O)
        board.captureSpot(Position.MID_CENTER, Player.X)
        board.captureSpot(Position.TOP_CENTER, Player.O)
        board.captureSpot(Position.MID_RIGHT, Player.X)

        board.reset()

        board.spots.forEach {
            assertTrue(it.mark.isEmpty())
        }
    }

    @Test
    fun `given game is over, when marking a spot, then through a game over exception`() {
        board.captureSpot(Position.TOP_LEFT, Player.X)
        board.captureSpot(Position.BOTTOM_LEFT, Player.O)
        board.captureSpot(Position.TOP_CENTER, Player.X)
        board.captureSpot(Position.BOTTOM_CENTER, Player.O)
        board.captureSpot(Position.TOP_RIGHT, Player.X)

        try {
            board.captureSpot(Position.BOTTOM_RIGHT, Player.O)
        } catch (exception: Exception) {
            assertEquals(Board.GameOverException, exception)
        }
    }

}
