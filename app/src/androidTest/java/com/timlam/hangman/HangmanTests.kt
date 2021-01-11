package hangman

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.timlam.tictactoe.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class HangmanTests {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val playerWinsMessage = context.getString(R.string.message_player_wins)

    @Test
    fun whensdf() {

    }

}
