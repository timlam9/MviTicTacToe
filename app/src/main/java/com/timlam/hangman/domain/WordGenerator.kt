package com.timlam.hangman.domain

import com.timlam.hangman.data.State
import kotlinx.coroutines.flow.Flow

interface WordGenerator {

    suspend fun generateRandomWord(): State<String>

    fun getList(): Flow<State<List<String>>>

}
