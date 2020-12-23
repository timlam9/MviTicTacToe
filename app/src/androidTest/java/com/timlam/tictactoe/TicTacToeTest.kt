package com.timlam.tictactoe

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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
    fun when_player_1_clicks_an_unmarked_spot_mark_it_as_X() {
        onSpotClicked(R.id.topLeftSpot)

        checkSpotForMark(R.id.topLeftSpot, Player.X.name)
    }

    @Test
    fun when_either_player_clicks_a_marked_spot_show_already_marked_message() {
        onSpotClicked(R.id.topLeftSpot)
        onSpotClicked(R.id.topLeftSpot)

        showAlreadyMarkedMessage()
    }

    @Test
    fun when_player_2_clicks_an_unmarked_spot_mark_it_as_O() {
        onSpotClicked(R.id.topLeftSpot)
        onSpotClicked(R.id.topCenterSpot)

        checkSpotForMark(R.id.topCenterSpot, Player.O.name)
    }

    @Test
    fun when_player_1_wins() {
        onSpotClicked(R.id.topLeftSpot)
        onSpotClicked(R.id.midLeftSpot)
        onSpotClicked(R.id.topCenterSpot)
        onSpotClicked(R.id.midCenterSpot)
        onSpotClicked(R.id.topRightSpot)

        showPlayerWinsMessage(Player.X)
    }

    @Test
    fun when_player_2_wins() {
        onSpotClicked(R.id.midLeftSpot)
        onSpotClicked(R.id.topLeftSpot)
        onSpotClicked(R.id.topCenterSpot)
        onSpotClicked(R.id.midCenterSpot)
        onSpotClicked(R.id.bottomLeftSpot)
        onSpotClicked(R.id.bottomRightSpot)

        showPlayerWinsMessage(Player.O)
    }


    @Test
    fun when_players_ties() {
        onSpotClicked(R.id.topLeftSpot)
        onSpotClicked(R.id.midLeftSpot)
        onSpotClicked(R.id.topCenterSpot)
        onSpotClicked(R.id.midCenterSpot)
        onSpotClicked(R.id.midRightSpot)
        onSpotClicked(R.id.topRightSpot)
        onSpotClicked(R.id.bottomLeftSpot)
        onSpotClicked(R.id.bottomCenterSpot)
        onSpotClicked(R.id.bottomRightSpot)

        showTieMessage()
    }


    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val playerWinsMessage = context.getString(R.string.message_player_wins)

    private fun showPlayerWinsMessage(player: Player) = onView(
        withId(com.google.android.material.R.id.snackbar_text)
    ).check(matches(withText(player.name + " " + playerWinsMessage)))

    private fun showTieMessage() =
        onView(withId(com.google.android.material.R.id.snackbar_text)).check(matches(withText(R.string.message_tie)))

    private fun onSpotClicked(@IdRes spotId: Int) = onView(withId(spotId)).perform(click())

    private fun checkSpotForMark(@IdRes spotId: Int, mark: String) = onView(withId(spotId)).check(matches(withText(mark)))

    private fun showAlreadyMarkedMessage() = onView(
        withId(com.google.android.material.R.id.snackbar_text)
    ).check(matches(withText(R.string.message_spot_already_marked)))

}
