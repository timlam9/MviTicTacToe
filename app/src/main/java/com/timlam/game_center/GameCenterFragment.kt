package com.timlam.game_center

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.timlam.main.Screen
import com.timlam.main.viewBinding
import com.timlam.tictactoe.R
import com.timlam.tictactoe.databinding.FragmentGameCenterBinding
import com.timlam.tictactoe.presentation.TicTacToeFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

class GameCenterFragment : Fragment(R.layout.fragment_game_center) {

    private val binding by viewBinding(FragmentGameCenterBinding::bind)

    @ExperimentalCoroutinesApi
    private val gamesAdapter = GamesAdapter {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, TicTacToeFragment())
            addToBackStack(null)
            commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gameCenterList.adapter = gamesAdapter
        gamesAdapter.submitList(listOf(Game(Screen.TicTacToe.name), Game(Screen.Hangman.name)))
    }

}
