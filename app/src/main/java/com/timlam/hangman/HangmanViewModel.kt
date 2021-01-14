package com.timlam.hangman

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HangmanViewModel(private val wordsGenerator: WordsGenerator = WordsGenerator()) : ViewModel() {

    private val _gameStatus = MutableStateFlow(GameStatus.PLAYING)
    val gameStatus: StateFlow<GameStatus> = _gameStatus

    private val _clickedCharacters = MutableStateFlow<Set<Char>>(emptySet())
    val clickedCharacters: StateFlow<Set<Char>> = _clickedCharacters

    private val _displayingWord = MutableStateFlow("")
    val displayingWord: StateFlow<String> = _displayingWord

    private val _lives = MutableStateFlow(5)

    private lateinit var word: Word

    init {
        _lives.onEach(::trackLives).launchIn(viewModelScope)
        startGame()
    }

    private fun startGame() {
        word = Word(wordsGenerator.generateRandomWord())
        _displayingWord.value = word.toString()
        _lives.value = 5
    }

    private fun trackLives(lives: Int) {
        _gameStatus.value = if (lives == 0) GameStatus.LOST else GameStatus.PLAYING
    }

    fun characterClicked(letter: Char) {
        if (_gameStatus.value == GameStatus.LOST) return

        if (!word.revealLetter(letter)) _lives.value--
        _displayingWord.value = word.toString()
        _clickedCharacters.value = _clickedCharacters.value + letter
    }

}
