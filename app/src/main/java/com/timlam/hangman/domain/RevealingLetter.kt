package com.timlam.hangman.domain

data class RevealingLetter(val letter: Char, var revealed: Boolean = false) {

    override fun toString(): String {
        return if (revealed) letter.toString() else "_"
    }

}
