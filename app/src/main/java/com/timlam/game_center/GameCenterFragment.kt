package com.timlam.game_center

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.timlam.hangman.presentation.HangmanFragment
import com.timlam.main.viewBinding
import com.timlam.tictactoe.R
import com.timlam.tictactoe.databinding.FragmentGameCenterBinding
import com.timlam.tictactoe.presentation.TicTacToeFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

class GameCenterFragment : Fragment(R.layout.fragment_game_center) {

    private val binding by viewBinding(FragmentGameCenterBinding::bind)

    @ExperimentalCoroutinesApi
    private val gamesAdapter = GamesAdapter { game ->
        val fragment = if (game.title == Games.TicTacToe.name) {
            TicTacToeFragment()
        } else {
            HangmanFragment()
        }

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gameCenterList.adapter = gamesAdapter
        gamesAdapter.submitList(listOf(Game(Games.TicTacToe.name), Game(Games.Hangman.name)))
    }

}
