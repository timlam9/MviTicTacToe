package com.timlam.hangman.domain

import com.timlam.hangman.data.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class GameEngine(
    private val wordsGenerator: WordGenerator
) {

    private val _displayingWord = MutableStateFlow("")
    val displayingWord: StateFlow<String> = _displayingWord

    private val _gameStatus = MutableStateFlow(GameStatus.PLAYING)
    val gameStatus: StateFlow<GameStatus> = _gameStatus

    private val _clickedCharacters = MutableStateFlow<Set<Char>>(emptySet())
    val clickedCharacters: StateFlow<Set<Char>> = _clickedCharacters

    private val _lives = MutableStateFlow(5)
    val lives: StateFlow<Int> = _lives

    private val _displayLoadingView = MutableStateFlow(true)
    val displayLoadingView = _displayLoadingView

    private var word: Word = Word("test")

    suspend fun startGame() {
        _displayLoadingView.value = true
        when (val state = wordsGenerator.generateRandomWord()) {
            is State.Loading -> _displayLoadingView.value = true
            is State.Success -> {
                _displayLoadingView.value = false
                word = Word(state.data)
                _displayingWord.value = word.toString()
                _clickedCharacters.value = emptySet()
                _lives.value = 5
                _gameStatus.value = GameStatus.PLAYING
            }
            is State.Failed -> _displayLoadingView.value = false
        }
    }

    fun playerAction(letter: Char) {
        if (_gameStatus.value != GameStatus.PLAYING) return
        if (!word.revealLetter(letter)) _lives.value--
        _displayingWord.value = word.toString()
        _clickedCharacters.value = _clickedCharacters.value + letter
    }

    fun trackLives(lives: Int) {
        _gameStatus.value = if (lives == 0) GameStatus.LOST else GameStatus.PLAYING
    }

    fun hasPlayerWon() {
        if (word.areAllLettersRevealed()) _gameStatus.value = GameStatus.WON
    }

    suspend fun getList() = withContext(Dispatchers.IO) {
        wordsGenerator.getList().onEach { state ->
            when (state) {
                is State.Loading -> _displayLoadingView.value = true
                is State.Success -> {
                    _displayLoadingView.value = false
                    state.data.shuffled().first()
                }
                is State.Failed -> _displayLoadingView.value = false
            }
        }.launchIn(this)
    }

}
