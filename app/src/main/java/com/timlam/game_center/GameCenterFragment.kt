package com.timlam.game_center

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.timlam.tictactoe.R
import com.timlam.tictactoe.databinding.FragmentGameCenterBinding
import com.timlam.viewBinding

class GameCenterFragment : Fragment(R.layout.fragment_game_center) {

    private val binding by viewBinding(FragmentGameCenterBinding::bind)
    private val gamesAdapter = GamesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gameCenterList.adapter = gamesAdapter
        gamesAdapter.submitList(listOf(Game("TicTacToe"), Game("Hangman")))
    }

}
