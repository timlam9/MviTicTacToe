package com.timlam.game_center

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.timlam.tictactoe.R
import com.timlam.tictactoe.presentation.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class GameCenterTests {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun when_app_launches_shows_games_center_screen() {

        isViewShown(R.id.gameCenterList)
    }

    private fun isViewShown(@IdRes viewId: Int) = Espresso.onView(ViewMatchers.withId(viewId))
        .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))


}
