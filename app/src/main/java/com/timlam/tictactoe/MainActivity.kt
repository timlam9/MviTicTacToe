package com.timlam.tictactoe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.timlam.tictactoe.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<TicTacToeViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        renderStates()
        handleEffects()
        eventsListener(mergeFlows())
    }

    private fun renderStates() {
        viewModel.state.onEach { render(it) }.launchIn(lifecycleScope)
    }

    private fun render(state: TicTacToeState) {
        binding.topLeftSpot.text = state.board.markOfSpot(Position.TOP_LEFT)
        binding.topCenterSpot.text = state.board.markOfSpot(Position.TOP_CENTER)
    }

    private fun handleEffects() = viewModel.effects.onEach { resolve(it) }.launchIn(lifecycleScope)

    private fun resolve(effect: Effect) {
        when (effect) {
            Effect.ShowAlreadyMarkedMessage -> showSnackbar(getString(R.string.message_spot_already_marked))
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun eventsListener(events: Flow<Event>) {
        events.onEach { viewModel.onEvent(it) }.launchIn(lifecycleScope)
    }

    private fun mergeFlows(): Flow<Event> =
        merge<Event>(
            binding.topLeftSpot.clicks().map { Event.OnSpotClicked(Position.TOP_LEFT) },
            binding.topCenterSpot.clicks().map { Event.OnSpotClicked(Position.TOP_CENTER) }
        )

}
