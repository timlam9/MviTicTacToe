package com.timlam.tictactoe

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.timlam.domain.models.Position
import com.timlam.domain.models.findMarkOfPosition
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
        binding.restart.isVisible = state.restart

        with(state.spots) {
            binding.topLeftSpot.text = findMarkOfPosition(Position.TOP_LEFT)
            binding.topCenterSpot.text = findMarkOfPosition(Position.TOP_CENTER)
            binding.topRightSpot.text = findMarkOfPosition(Position.TOP_RIGHT)
            binding.midLeftSpot.text = findMarkOfPosition(Position.MID_LEFT)
            binding.midCenterSpot.text = findMarkOfPosition(Position.MID_CENTER)
            binding.midRightSpot.text = findMarkOfPosition(Position.MID_RIGHT)
            binding.bottomLeftSpot.text = findMarkOfPosition(Position.BOTTOM_LEFT)
            binding.bottomCenterSpot.text = findMarkOfPosition(Position.BOTTOM_CENTER)
            binding.bottomRightSpot.text = findMarkOfPosition(Position.BOTTOM_RIGHT)
        }
    }

    private fun handleEffects() = viewModel.effects.onEach { resolve(it) }.launchIn(lifecycleScope)

    private fun resolve(effect: Effect) {
        when (effect) {
            is Effect.ShowAlreadyMarkedMessage -> showSnackbar(getString(R.string.message_spot_already_marked))
            is Effect.ShowPlayerWinsMessage -> showSnackbar(effect.player.name + " " + getString(R.string.message_player_wins))
            is Effect.ShowTieMessage -> showSnackbar(getString(R.string.message_tie))
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
            binding.topCenterSpot.clicks().map { Event.OnSpotClicked(Position.TOP_CENTER) },
            binding.topRightSpot.clicks().map { Event.OnSpotClicked(Position.TOP_RIGHT) },
            binding.midLeftSpot.clicks().map { Event.OnSpotClicked(Position.MID_LEFT) },
            binding.midCenterSpot.clicks().map { Event.OnSpotClicked(Position.MID_CENTER) },
            binding.midRightSpot.clicks().map { Event.OnSpotClicked(Position.MID_RIGHT) },
            binding.bottomLeftSpot.clicks().map { Event.OnSpotClicked(Position.BOTTOM_LEFT) },
            binding.bottomCenterSpot.clicks().map { Event.OnSpotClicked(Position.BOTTOM_CENTER) },
            binding.bottomRightSpot.clicks().map { Event.OnSpotClicked(Position.BOTTOM_RIGHT) }
        )

}
