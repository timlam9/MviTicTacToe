package com.timlam.hangman.di

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.timlam.hangman.data.GameDispatchers
import com.timlam.hangman.data.RemoteWordGenerator
import com.timlam.hangman.domain.GameEngine
import com.timlam.hangman.domain.WordGenerator
import com.timlam.hangman.presentation.HangmanViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val presentation = module {

    viewModel { HangmanViewModel(get(), get()) }
}

private val domain = module {
    factory<WordGenerator> { RemoteWordGenerator(get()) }
    factory { GameEngine(get()) }
}

private val data = module {
    single { GameDispatchers() }
    single { Firebase.firestore }
}

val hangmanModule = listOf(presentation, domain, data)
