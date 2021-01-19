package com.timlam.hangman.data

import com.timlam.hangman.domain.WordGenerator
import kotlinx.coroutines.flow.Flow

class LocalWordGenerator : WordGenerator<String> {

    private val words: List<String> = listOf("titanomegistos", "paixtaras", "coffee", "bulldog")

    override suspend fun generateRandomWord(): String = words.shuffled().first()

    override fun getList(): Flow<List<String>> {
        TODO("Not yet implemented")
    }

}
