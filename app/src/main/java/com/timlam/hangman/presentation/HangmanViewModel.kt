package com.timlam.hangman.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timlam.hangman.data.GameDispatchers
import com.timlam.hangman.domain.GameEngine
import com.timlam.hangman.domain.GameStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HangmanViewModel(
    private val dispatcher: GameDispatchers,
    private val gameEngine: GameEngine
) : ViewModel() {

    val gameStatus: StateFlow<GameStatus> = gameEngine.gameStatus
    val word: StateFlow<String> = gameEngine.displayingWord
    val alreadySelectedCharacters: StateFlow<Set<Char>> = gameEngine.clickedCharacters
    val lives: StateFlow<Int> = gameEngine.lives

    private val _displayLoadingView = MutableStateFlow(true)
    val displayLoadingView = _displayLoadingView

    init {
        viewModelScope.launch(dispatcher.ioDispatcher) {
            gameEngine.startGame()
            gameEngine.lives.onEach { gameEngine.trackLives(it) }.launchIn(viewModelScope)
            gameEngine.clickedCharacters.onEach { gameEngine.hasPlayerWon() }.launchIn(viewModelScope)
            gameEngine.displayLoadingView.onEach { _displayLoadingView.value = it }.launchIn(viewModelScope)
        }
    }


    fun characterClicked(letter: Char) {
        gameEngine.playerAction(letter)
    }

    fun restartButtonClicked() {
        viewModelScope.launch(dispatcher.ioDispatcher) {
            gameEngine.startGame()
        }
    }

}

