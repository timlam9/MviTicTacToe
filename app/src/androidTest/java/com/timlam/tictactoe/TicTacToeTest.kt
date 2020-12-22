package com.timlam.tictactoe

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class TicTacToeTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun clickingASpotAddsAnXMark() {
        onTopLeftSpotClicked()
        topLeftSpotIsMarkedX()
    }

    private fun onTopLeftSpotClicked() = onView(withId(R.id.topLeftSpot)).perform(click())

    private fun topLeftSpotIsMarkedX() = onView(withId(R.id.topLeftSpot)).check(matches(withText("X")))


}
