package com.timlam.hangman

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.timlam.main.viewBinding
import com.timlam.tictactoe.R
import com.timlam.tictactoe.databinding.FragmentHangmanBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HangmanFragment : Fragment(R.layout.fragment_hangman) {

    private val binding by viewBinding(FragmentHangmanBinding::bind)
    private val viewModel by viewModels<HangmanViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
        viewModel.displayingWord.onEach { binding.hangmanWord.text = it }.launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.clickedCharacters.onEach(::nullifyButtons).launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.gameStatus.onEach(::handleGameStatus).launchIn(viewLifecycleOwner.lifecycleScope)
        viewModel.lives.onEach { binding.hangmanLives.text = getString(R.string.lives, it.toString()) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun nullifyButtons(set: Set<Char>) {
        binding.buttonsContainer.children.filterIsInstance<Button>()
            .forEach { button ->
                if (set.contains(button.text.first())) {
                    button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.black))
                    button.setOnClickListener(null)
                } else {
                    button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                    button.setOnClickListener {
                        viewModel.characterClicked(button.text.first())
                    }
                }
            }
    }

    private fun handleGameStatus(gameStatus: GameStatus) {
        if (gameStatus == GameStatus.LOST)
            Snackbar.make(binding.root, "You lost noob!", Snackbar.LENGTH_SHORT).show()
        else if (gameStatus == GameStatus.WON)
            Snackbar.make(binding.root, "What a player! You nailed it!", Snackbar.LENGTH_SHORT).show()

        binding.restartButton.isVisible = gameStatus != GameStatus.PLAYING
    }

    private fun initializeUI() {
        ('A'..'Z').forEach { letter ->
            Button(context).apply {
                text = letter.toString()
                setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
                binding.buttonsContainer.addView(this)
                setOnClickListener {
                    viewModel.characterClicked(letter)
                }
            }
        }
        binding.restartButton.setOnClickListener { viewModel.restartButtonClicked() }
    }

}
