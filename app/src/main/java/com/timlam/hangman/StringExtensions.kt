package com.timlam.hangman

fun String.appendSpacesBetweenChars(): String = fold("") { word, char -> "$word$char " }
