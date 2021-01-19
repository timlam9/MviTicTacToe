package com.timlam.hangman.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

data class GameDispatchers(val ioDispatcher: CoroutineDispatcher = Dispatchers.IO)
