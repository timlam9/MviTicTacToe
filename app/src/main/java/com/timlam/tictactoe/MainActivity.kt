package com.timlam.tictactoe

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.timlam.tictactoe.databinding.ActivityMainBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<TicTacToeViewModel>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        eventsListener(mergeFlows())
        renderStates()

        val lambda: suspend (Unit) -> Event.OnSpotClicked = { Event.OnSpotClicked(Spot.TOP_LEFT) }
        val x = binding.topLeftSpot.clicks().map(lambda)

    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun eventsListener(events: Flow<Event>) {
        events.onEach { viewModel.onEvent(it) }.launchIn(lifecycleScope)
    }

    private fun mergeFlows(): Flow<Event> =
        merge<Event>(binding.topLeftSpot.clicks().map { Event.OnSpotClicked(Spot.TOP_LEFT) })

    private fun renderStates() {
        viewModel.state.onEach { render(it) }.launchIn(lifecycleScope)
    }

    private fun render(state: TicTacToeState) {
        binding.topLeftSpot.text = state.topLeftSpot
    }

    private fun View.clicks(): Flow<Unit> = callbackFlow {
        this@clicks.setOnClickListener {
            this.offer(Unit)
        }
        awaitClose { this@clicks.setOnClickListener(null) }
    }

}
