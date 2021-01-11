package com.timlam.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.timlam.game_center.GameCenterFragment
import com.timlam.tictactoe.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, GameCenterFragment())
            addToBackStack("root")
        }.commit()

    }

}
