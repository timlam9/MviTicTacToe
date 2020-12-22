package com.timlam.tictactoe

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class TicTacToeViewModelTest {

    private val viewModel = TicTacToeViewModel()

    @Test
    fun `when a spot is clicked, then mark it as X`() = runBlockingTest {
        val states = mutableListOf<TicTacToeState>()

        viewModel.onEvent(Event.OnSpotClicked(Spot.TOP_LEFT))

        val job = launch {
            viewModel.state.collect {
                states.add(it)
            }
        }

        assertEquals(TicTacToeState("X"), states[0])
        job.cancel()
    }

}
