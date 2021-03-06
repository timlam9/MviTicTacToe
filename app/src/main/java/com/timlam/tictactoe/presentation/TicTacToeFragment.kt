package com.timlam.tictactoe.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.timlam.main.clicks
import com.timlam.main.viewBinding
import com.timlam.tictactoe.R
import com.timlam.tictactoe.databinding.FragmentTictactoeBinding
import com.timlam.tictactoe.domain.Position
import com.timlam.tictactoe.domain.findMarkOfPosition
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
class TicTacToeFragment : Fragment(R.layout.fragment_tictactoe) {

    private val viewModel by viewModels<TicTacToeViewModel>()
    private val binding by viewBinding(FragmentTictactoeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            is Effect.ShowGameOverMessageMessage -> showSnackbar(getString(R.string.message_game_over))
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun eventsListener(events: Flow<Event>) {
        events.onEach { viewModel.onEvent(it) }.launchIn(lifecycleScope)
    }


    private fun mergeFlows(): Flow<Event> = merge(
        binding.topLeftSpot.clicks().map { Event.OnSpotClicked(Position.TOP_LEFT) },
        binding.topCenterSpot.clicks().map { Event.OnSpotClicked(Position.TOP_CENTER) },
        binding.topRightSpot.clicks().map { Event.OnSpotClicked(Position.TOP_RIGHT) },
        binding.midLeftSpot.clicks().map { Event.OnSpotClicked(Position.MID_LEFT) },
        binding.midCenterSpot.clicks().map { Event.OnSpotClicked(Position.MID_CENTER) },
        binding.midRightSpot.clicks().map { Event.OnSpotClicked(Position.MID_RIGHT) },
        binding.bottomLeftSpot.clicks().map { Event.OnSpotClicked(Position.BOTTOM_LEFT) },
        binding.bottomCenterSpot.clicks().map { Event.OnSpotClicked(Position.BOTTOM_CENTER) },
        binding.bottomRightSpot.clicks().map { Event.OnSpotClicked(Position.BOTTOM_RIGHT) },
        binding.restart.clicks().map { Event.OnRestartClicked }
    )

}


