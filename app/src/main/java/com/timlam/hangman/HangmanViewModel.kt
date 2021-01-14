package com.timlam.hangman

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HangmanViewModel(private val wordsGenerator: WordsGenerator = WordsGenerator()) : ViewModel() {

    private val _displayingWord = MutableStateFlow("")
    val displayingWord: StateFlow<String> = _displayingWord

    private var revealingWord = listOf<RevealingLetter>()
    private var playingWord = ""

    private fun generateUnderscoreWord(): String = revealingWord.fold("") { word, revealingLetter ->
        if (revealingLetter.revealed) "$word${revealingLetter.letter}" else "${word}_"
    }

    private fun revealLetter(letter: Char) {
        revealingWord.filter { it.letter.equals(letter, true) }.forEach {
            it.revealed = true
        }
    }

    private fun String.generateSpacesWord(): String = fold("") { word, char -> "$word$char " }

    private fun String.generateRevealingWord(): List<RevealingLetter> = map { RevealingLetter(it) }

    fun startGame() {
        playingWord = wordsGenerator.generateRandomWord()
        revealingWord = playingWord.generateRevealingWord()
        _displayingWord.value = generateUnderscoreWord().generateSpacesWord().trimEnd()
    }

    fun characterClicked(letter: Char) {
        if (playingWord.contains(letter, true)) {
            revealLetter(letter)
            _displayingWord.value = generateUnderscoreWord().generateSpacesWord().trimEnd()
        }
    }

}
