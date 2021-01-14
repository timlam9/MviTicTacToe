package com.timlam.hangman

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
        generateGrid()

        viewModel.displayingWord.onEach {
            binding.hangmanWord.text = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.clickedCharacters.onEach { set ->
            binding.buttonsContainer.children.filterIsInstance<Button>()
                .filter { it.text.toString().any { char -> set.contains(char) } }.forEach {
                    it.background = binding.root.background
                    it.setOnClickListener(null)
                }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.startGame()
    }

    private fun generateGrid() {
        ('A'..'Z').forEach { letter ->
            Button(context).apply {
                text = letter.toString()
                binding.buttonsContainer.addView(this)
                setOnClickListener {
                    viewModel.characterClicked(letter)
                }
            }
        }
    }

}