package com.timlam.hangman

data class RevealingLetter(val letter: Char, var revealed: Boolean = false) {

    override fun toString(): String {
        return if (revealed) letter.toString() else "_"
    }

}
