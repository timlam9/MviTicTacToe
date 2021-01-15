package com.timlam.hangman

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameEngine(private val wordsGenerator: WordsGenerator) {

    private val _displayingWord = MutableStateFlow("")
    val displayingWord: StateFlow<String> = _displayingWord

    private val _gameStatus = MutableStateFlow(GameStatus.PLAYING)
    val gameStatus: StateFlow<GameStatus> = _gameStatus

    private val _clickedCharacters = MutableStateFlow<Set<Char>>(emptySet())
    val clickedCharacters: StateFlow<Set<Char>> = _clickedCharacters

    private val _lives = MutableStateFlow(5)
    val lives: StateFlow<Int> = _lives

    private lateinit var word: Word

    fun startGame() {
        word = Word(wordsGenerator.generateRandomWord())
        _displayingWord.value = word.toString()
        _clickedCharacters.value = emptySet()
        _lives.value = 5
        _gameStatus.value = GameStatus.PLAYING
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

}
