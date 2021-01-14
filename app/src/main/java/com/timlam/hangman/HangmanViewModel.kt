package com.timlam.hangman

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HangmanViewModel(private val wordsGenerator: WordsGenerator = WordsGenerator()) : ViewModel() {

    private val _displayingWord = MutableStateFlow("")
    val displayingWord: StateFlow<String> = _displayingWord

    private lateinit var word: Word

    fun startGame() {
        word = Word(wordsGenerator.generateRandomWord())
        _displayingWord.value = word.toString()
    }

    fun characterClicked(letter: Char) {
        word.revealLetter(letter)
        _displayingWord.value = word.toString()
    }

}
