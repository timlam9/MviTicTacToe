package com.timlam.hangman

import com.timlam.hangman.data.GameDispatchers
import com.timlam.hangman.data.LocalWordGenerator
import com.timlam.hangman.domain.GameEngine
import com.timlam.hangman.domain.GameStatus
import com.timlam.hangman.domain.Word
import com.timlam.hangman.presentation.HangmanViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HangmanViewModelTests {

    // Responsibilities
    // WordsGenerator : Generate random words
    private val wordsGenerator = mockk<LocalWordGenerator>()

    // ViewModel: Adapter between view and business logic
    private lateinit var viewModel: HangmanViewModel

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val gameDispatchers = GameDispatchers(testCoroutineDispatcher)

    private val randomPlayingWord = "titanomegistos"

    @Before
    fun before() {
        Dispatchers.setMain(testCoroutineDispatcher)
        coEvery { wordsGenerator.generateRandomWord() } returns randomPlayingWord
        viewModel = HangmanViewModel(gameDispatchers, GameEngine(wordsGenerator))
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when a game starts pick a random word and display it with underscores`() {
        val displayedWordSize = (randomPlayingWord.length * 2) - 1

        assertEquals(displayedWordSize, viewModel.word.value.length)
    }

    @Test
    fun `display clicked character if the word contains it`() {
        val clickedChar = 't'
        val semiRevealedWord = Word(randomPlayingWord).apply { revealLetter(clickedChar) }

        viewModel.characterClicked(clickedChar)

        assertEquals(semiRevealedWord.toString(), viewModel.word.value)
    }

    @Test
    fun `clicking a character renders it pressed`() {
        viewModel.characterClicked('o')

        assertEquals(setOf('o'), viewModel.alreadySelectedCharacters.value)
    }

    @Test
    fun `on consecutive errors you lose the game`() {
        viewModel.characterClicked('v')
        viewModel.characterClicked('w')
        viewModel.characterClicked('x')
        viewModel.characterClicked('y')
        viewModel.characterClicked('z')

        assertEquals(GameStatus.LOST, viewModel.gameStatus.value)
    }

    @Test
    fun `when restart button is clicked then restart the game`() {
        viewModel.restartButtonClicked()

        assertEquals(GameStatus.PLAYING, viewModel.gameStatus.value)
    }

    @Test
    fun `when clicking all the correct letters you win the game`() {
        viewModel.characterClicked('t')
        viewModel.characterClicked('i')
        viewModel.characterClicked('a')
        viewModel.characterClicked('n')
        viewModel.characterClicked('o')
        viewModel.characterClicked('m')
        viewModel.characterClicked('e')
        viewModel.characterClicked('g')
        viewModel.characterClicked('s')

        assertEquals(GameStatus.WON, viewModel.gameStatus.value)
    }

}
