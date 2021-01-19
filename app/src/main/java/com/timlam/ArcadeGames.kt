package com.timlam

import android.app.Application
import com.timlam.hangman.di.hangmanModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ArcadeGames : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ArcadeGames)
            modules(hangmanModule)
        }
    }

}
