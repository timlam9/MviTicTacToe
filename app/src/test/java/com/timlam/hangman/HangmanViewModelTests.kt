package com.timlam.hangman

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HangmanViewModelTests {

    // Responsibilities
    // WordsGenerator : Generate random words
    private val wordsGenerator = mockk<WordsGenerator>()

    // ViewModel: Adapter between view and business logic
    private val viewModel = HangmanViewModel(wordsGenerator)
    private val randomPlayingWord = "titanomegistos"

    @Before
    fun before() {
        every { wordsGenerator.generateRandomWord() } returns randomPlayingWord
        viewModel.startGame()
    }

    @Test
    fun `when a game starts pick a random word and display it with underscores`() {
        val displayedWordSize = (randomPlayingWord.length * 2) - 1

        assertEquals(displayedWordSize, viewModel.displayingWord.value.length)
    }

    @Test
    fun `display clicked character if the word contains it`() {
        val clickedChar = 't'
        val semiRevealedWord = Word(randomPlayingWord).apply { revealLetter(clickedChar) }

        viewModel.characterClicked(clickedChar)

        assertEquals(semiRevealedWord.toString(), viewModel.displayingWord.value)
    }

    @Test
    fun `clicking a character renders it pressed`() {
        viewModel.characterClicked('o')

        assertEquals(setOf('o'), viewModel.clickedCharacters.value)
    }

}
