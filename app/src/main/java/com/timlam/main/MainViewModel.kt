package com.timlam.main

import androidx.lifecycle.ViewModel
import com.timlam.game_center.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _games = MutableStateFlow(Screen.GameCenter)
    val games: StateFlow<Screen> = _games

    fun gameClicked(game: Game) {
        _games.value = if (game.title == Screen.TicTacToe.name) {
            Screen.TicTacToe
        } else {
            Screen.Hangman
        }
    }

}

enum class Screen {

    GameCenter,
    TicTacToe,
    Hangman

}
