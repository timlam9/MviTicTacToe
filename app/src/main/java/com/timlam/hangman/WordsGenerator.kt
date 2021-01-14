package com.timlam.hangman

class WordsGenerator {

    private val words: List<String> = listOf("titanomegistos", "paixtaras", "coffee", "bulldog")

    fun generateRandomWord(): String = words.shuffled().first()

}
