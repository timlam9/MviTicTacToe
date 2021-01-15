package com.timlam.hangman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HangmanViewModel(private val gameEngine: GameEngine = GameEngine(WordsGenerator())) : ViewModel() {

    val gameStatus: StateFlow<GameStatus> = gameEngine.gameStatus
    val word: StateFlow<String> = gameEngine.displayingWord
    val alreadySelectedCharacters: StateFlow<Set<Char>> = gameEngine.clickedCharacters
    val lives: StateFlow<Int> = gameEngine.lives


    init {
        gameEngine.startGame()
        gameEngine.lives.onEach { gameEngine.trackLives(it) }.launchIn(viewModelScope)
        gameEngine.clickedCharacters.onEach { gameEngine.hasPlayerWon() }.launchIn(viewModelScope)
    }


    fun characterClicked(letter: Char) {
        gameEngine.playerAction(letter)
    }

    fun restartButtonClicked() {
        gameEngine.startGame()
    }

}

