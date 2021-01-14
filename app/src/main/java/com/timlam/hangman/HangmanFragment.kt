package com.timlam.hangman

import android.os.Bundle
import android.view.View
import android.widget.Button
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

        viewModel.displayingWord.onEach {
            binding.hangmanWord.text = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.startGame()

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
