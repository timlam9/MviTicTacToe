package com.timlam.hangman

class Word(value: String) {

    private val revealingLetters: List<RevealingLetter>

    init {
        revealingLetters = value.generateRevealingWord()
    }

    fun revealLetter(letter: Char): Boolean {
        revealingLetters.filter { it.letter.equals(letter, true) }.also { list ->
            list.forEach {
                it.revealed = true
            }
            return list.count() > 0
        }
    }

    fun areAllLettersRevealed() = revealingLetters.filter { it.revealed }.size == revealingLetters.size

    private fun String.generateRevealingWord(): List<RevealingLetter> = map { RevealingLetter(it) }

    override fun toString(): String {
        return revealingLetters.joinToString(" ") { it.toString() }.trimEnd()
    }

}
