package pawnChess
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.Writer

internal class PositionTest {


    @Test
    fun `test black wins by capturing all white pieces`() {

        // given a player and a board where black can capture all whites pieces in one move

        var board = BoardFactory.getBoard("8/p7/1P6/8/8/8/p7/8 b - -")
        val position = Position(board)
        assertEquals(false, position.gameStateEnd(GameState.PLAYING, board.colorToMove))

        // when we make the move
        val move = Move.createMove(Square.A7, Square.B6)

        if (MoveGen.isLegalMove(board, move)) {
            board.doMove(move)
            board.printBoard()
        } else assertFalse(true)

        // then we check that black wins with correct leaveMessage

        assertEquals(true, position.gameStateEnd(GameState.PLAYING, board.colorToMove))
        assertEquals("Black Wins!\nBye!", position.leaveMessage)

    }

    @Test
    fun `test stalemate for white if cannot move`() {

        // given a board with a stalemate after one black move
        var board = BoardFactory.getBoard("8/8/8/8/p7/8/P7/8 w - -")
        board.printBoard()

        // when we make the move
        var move = Move.createMove(Square.A2, Square.A3)
        if (MoveGen.isLegalMove(board, move)) {
            board.doMove(move)
            board.printBoard()
        }
        var position = Position(board)

        // then the game ends as stalemate and we check the leavemessage

        assertEquals(true, position.gameStateEnd(GameState.GAMEDRAWN, board.colorToMove))
        //       assertEquals(true, player.gameFinished(board,GameState.GAMEDRAWN))
        assertEquals("Stalemate!\nBye!", position.leaveMessage)
    }

    @Test
    fun `create a unique file using a name followed by a run number`() {
        // given a board, starting position
        var board = BoardFactory.getBoard()
        val position = Position(board)

        // when we create a file with chosen name
        val fileName = Position.createRunName("run, 0")
        //then filename is as expected
        assertEquals("run_0",fileName)

        // then the file should be created as a buffered reader
        var bw = Position.createFile(
            
        )

        assertEquals(br of BufferedWriter(Writer))

    }
}
