package com.timlam.hangman.domain

import kotlinx.coroutines.flow.Flow

interface WordGenerator<T> {

    suspend fun generateRandomWord(): T

    fun getList(): Flow<List<T>>

}
