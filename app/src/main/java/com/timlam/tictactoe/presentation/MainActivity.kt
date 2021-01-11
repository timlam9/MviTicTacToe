package com.timlam.tictactoe.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timlam.tictactoe.R
import com.timlam.tictactoe.game_center.GameCenterFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, GameCenterFragment())
        }.commit()

    }

}
