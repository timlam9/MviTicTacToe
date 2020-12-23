import com.timlam.domain.Board
import com.timlam.domain.GameEngine
import com.timlam.domain.models.GameStatus
import com.timlam.domain.models.Player
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class GameEngineTest {

    private val board = mockk<Board>()
    private val gameEngine = GameEngine()

    @Test
    fun `given board is won by player X, when updating game status, then game status is game over with player X as a winner`() {
        every { board.isWon(Player.X) } returns true

        val status = gameEngine.updateStatus(board, Player.X)

        assertEquals(status, GameStatus.GameOver.PlayerWon(Player.X))
    }

    @Test
    fun `given board is completed but not won, when updating game status, then game status is game over with tie`() {
        every { board.isWon(any()) } returns false
        every { board.isFull() } returns true

        val status = gameEngine.updateStatus(board, mockk())

        assertEquals(status, GameStatus.GameOver.Tie)
    }

    @Test
    fun `given board is not completed, when updating game status, then game status is playing`() {
        every { board.isWon(any()) } returns false
        every { board.isFull() } returns false

        val status = gameEngine.updateStatus(board, mockk())

        assertEquals(status, GameStatus.Playing)
    }

}
