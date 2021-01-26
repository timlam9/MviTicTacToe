package com.timlam.hangman.data

import com.timlam.hangman.domain.WordGenerator
import kotlinx.coroutines.flow.Flow

class LocalWordGenerator : WordGenerator {

    private val words: List<String> = listOf("titanomegistos", "paixtaras", "coffee", "bulldog")

    override suspend fun generateRandomWord(): State<String> = State.success(words.shuffled().first())

    override fun getList(): Flow<State<List<String>>> {
        TODO("Not yet implemented")
    }

}
