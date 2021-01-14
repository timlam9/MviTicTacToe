package com.timlam.hangman

class Word(value: String) {

    private val revealingLetters: List<RevealingLetter>

    init {
        revealingLetters = value.generateRevealingWord()
    }

    fun revealLetter(letter: Char) {
        revealingLetters.filter { it.letter.equals(letter, true) }.forEach {
            it.revealed = true
        }
    }

    private fun generateUnderscoreWord(): String = revealingLetters.fold("") { word, revealingLetter ->
        if (revealingLetter.revealed) "$word${revealingLetter.letter}" else "${word}_"
    }

    private fun String.generateRevealingWord(): List<RevealingLetter> = map { RevealingLetter(it) }

    override fun toString(): String {
        return generateUnderscoreWord().appendSpacesBetweenChars().trimEnd()
    }

}
