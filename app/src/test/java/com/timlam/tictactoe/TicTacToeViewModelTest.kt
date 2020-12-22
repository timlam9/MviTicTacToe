package com.timlam.tictactoe

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
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
    fun `when a spot is clicked, then mark it as X`() = runBlockingTest {
        val states = mutableListOf<TicTacToeState>()
        val job = viewModel.state.onEach { states.add(it) }.launchIn(this)

        viewModel.onEvent(Event.OnSpotClicked(Spot.TOP_LEFT))

        assertEquals(TicTacToeState("X"), states.last())
        job.cancel()
    }

    @Test
    fun `given a spot is already marked, when a spot is clicked, then show already marked message`() = runBlockingTest {
        val effects = mutableListOf<Effect>()
        viewModel.onEvent(Event.OnSpotClicked(Spot.TOP_LEFT))
        val job = viewModel.effects.onEach { effects.add(it) }.launchIn(this)

        viewModel.onEvent(Event.OnSpotClicked(Spot.TOP_LEFT))

        assertEquals(Effect.ShowAlreadyMarkedMessage, effects.first())
        job.cancel()
    }

}
