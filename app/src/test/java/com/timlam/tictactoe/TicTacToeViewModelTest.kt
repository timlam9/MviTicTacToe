package com.timlam.tictactoe

import android.net.sip.SipSession
import com.timlam.domain.models.GameStatus
import com.timlam.domain.models.Position
import com.timlam.domain.models.Player
import com.timlam.domain.models.Spot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TicTacToeViewModelTest {

    private val viewModel = TicTacToeViewModel()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testCoroutineDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given player 1 is playing, when a spot is clicked, then mark it as X`() = runBlockingTest {
        val states = mutableListOf<TicTacToeState>()
        val job = viewModel.state.onEach { states.add(it) }.launchIn(this)

        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_LEFT))

        assertEquals(
            expectedSpot(Position.TOP_LEFT, Player.X),
            actualSpotInBoard(states.last(), Position.TOP_LEFT)
        )
        job.cancel()
    }

    @Test
    fun `given a spot is already marked, when a spot is clicked, then show already marked message`() = runBlockingTest {
        val effects = mutableListOf<Effect>()
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_LEFT))
        val job = viewModel.effects.onEach { effects.add(it) }.launchIn(this)

        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_LEFT))

        assertEquals(Effect.ShowAlreadyMarkedMessage, effects.first())
        job.cancel()
    }

    @Test
    fun `given player 2 is playing, when a spot is clicked, then mark it as O`() = runBlockingTest {
        val states = mutableListOf<TicTacToeState>()
        val job = viewModel.state.onEach { states.add(it) }.launchIn(this)
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_LEFT))

        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_CENTER))

        assertEquals(
            expectedSpot(Position.TOP_LEFT, Player.X),
            actualSpotInBoard(states.last(), Position.TOP_LEFT)
        )
        assertEquals(
            expectedSpot(Position.TOP_CENTER, Player.O),
            actualSpotInBoard(states.last(), Position.TOP_CENTER)
        )
        job.cancel()
    }

    @Test
    fun `when player 1 wins, then show player 1 wins message`() = runBlockingTest {
        val effects = mutableListOf<Effect>()
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_CENTER))
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_CENTER))
        val job = viewModel.effects.onEach { effects.add(it) }.launchIn(this)

        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_RIGHT))

        assertEquals(Effect.ShowPlayerWinsMessage(Player.X), effects.first())
        job.cancel()
    }

    @Test
    fun `when player 2 wins, then show player 2 wins message`() = runBlockingTest {
        val effects = mutableListOf<Effect>()
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_CENTER))
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_CENTER))
        viewModel.onEvent(Event.OnSpotClicked(Position.BOTTOM_LEFT))
        val job = viewModel.effects.onEach { effects.add(it) }.launchIn(this)

        viewModel.onEvent(Event.OnSpotClicked(Position.BOTTOM_RIGHT))

        assertEquals(Effect.ShowPlayerWinsMessage(Player.O), effects.first())
        job.cancel()
    }

    @Test
    fun `when none player wins, then show tie message`() = runBlockingTest {
        val effects = mutableListOf<Effect>()
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_CENTER))
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_CENTER))
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_RIGHT))
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_RIGHT))
        viewModel.onEvent(Event.OnSpotClicked(Position.BOTTOM_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.BOTTOM_CENTER))
        val job = viewModel.effects.onEach { effects.add(it) }.launchIn(this)

        viewModel.onEvent(Event.OnSpotClicked(Position.BOTTOM_RIGHT))

        assertEquals(Effect.ShowTieMessage, effects.first())
        job.cancel()
    }

    @Test
    fun `when game ends, then restart button should be visible`() = runBlockingTest {
        val states = mutableListOf<TicTacToeState>()
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_CENTER))
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_CENTER))
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_RIGHT))
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_RIGHT))
        viewModel.onEvent(Event.OnSpotClicked(Position.BOTTOM_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.BOTTOM_CENTER))
        val job = viewModel.state.onEach { states.add(it) }.launchIn(this)

        viewModel.onEvent(Event.OnSpotClicked(Position.BOTTOM_RIGHT))

        assertTrue(states.last().restart)
        job.cancel()
    }

    @Test
    fun `when restart button is clicked, then reset the board`() = runBlockingTest {
        val states = mutableListOf<TicTacToeState>()
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_CENTER))
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_CENTER))
        viewModel.onEvent(Event.OnSpotClicked(Position.MID_RIGHT))
        viewModel.onEvent(Event.OnSpotClicked(Position.TOP_RIGHT))
        viewModel.onEvent(Event.OnSpotClicked(Position.BOTTOM_LEFT))
        viewModel.onEvent(Event.OnSpotClicked(Position.BOTTOM_CENTER))
        viewModel.onEvent(Event.OnSpotClicked(Position.BOTTOM_RIGHT))
        val job = viewModel.state.onEach { states.add(it) }.launchIn(this)

        viewModel.onEvent(Event.OnRestartClicked)

        assertTrue(states.last().gameStatus is GameStatus.Playing)
        assertTrue(states.last().spots.all { it.mark.isEmpty() })
        job.cancel()
    }

    private fun expectedSpot(position: Position, player: Player): Spot =
        Spot(position, player.name)

    private fun actualSpotInBoard(state: TicTacToeState, position: Position): Spot =
        state.spots.first { it.position == position }

}
