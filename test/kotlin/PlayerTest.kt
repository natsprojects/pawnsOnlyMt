package pawnChess
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class PlayerTest {

 /*   @Test
    fun `test starter game position using move series`() {
        // given a player and a board
        var board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -")
        assertEquals(Color.WHITE, board.colorToMove)
        var player1 = Player("WHITE", board, Color.WHITE)
        assertEquals(false, player1.gameFinished(board))

        var move = Move.createMove(Square.A2, Square.A4)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        var player2 = Player("Black", board, Color.BLACK)
        assertEquals(false, player2.gameFinished(board))
        assertEquals(Color.BLACK, board.colorToMove)
        board.printBoard()

        move = Move.createMove(Square.A7, Square.A5)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }

        assertEquals(false, player1.gameFinished(board))
        board.printBoard()
        assertEquals(Color.WHITE, board.colorToMove)

        move = Move.createMove(Square.A4, Square.A5)
        if (MoveGen.isLegalMove(board, move)) {
            board.doMove(move)
        }

        assertEquals(Color.WHITE, board.colorToMove)
        board.printBoard()

        assertEquals(false, player1.gameFinished(board))
        move = Move.createMove(Square.A7, Square.A5)
        if (MoveGen.isLegalMove(board, move)) {
            board.doMove(move)
        }
        board.printBoard()
        assertEquals(false, player2.gameFinished(board))
    }

    @Test
    fun `test starter game position has 16 legal moves for white`() {
        // given a player and a board
        var board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -")
        var legalMoves = MoveGen.generateLegalMoves(board, Color.WHITE)
        assertEquals(16, legalMoves.size)
        assertEquals(16, board.legalMoves.size)
        val player = Player("White", board, Color.WHITE)
        assertEquals(false, player.gameFinished(board))
    }

    @Test
    fun `test white wins by reaching 8th rank`() {

        // given a player and a board so white moves to the 8th rank
        var board = BoardFactory.getBoard("8/P7/1p6/8/8/8/8 w - -")

        val player = Player("White", board, Color.WHITE)
        assertEquals(false, player.gameFinished(board))
        val move = Move.createMove(Square.A7, Square.A8)

        // when we make a move
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
            board.printBoard()
        } else assertFalse(true)

        // then white should win the game with correct leaveMessage

        assertEquals(true, player.gameFinished(board))
        assertEquals(true, player.gameFinished(board,GameState.HASWON))
        assertEquals("White Wins!\nBye!", player.leaveMessage)
    }

    @Test
    fun `test black wins by capturing all white pieces`() {

        // given a player and a board where black can capture all whites pieces in one move

        var board = BoardFactory.getBoard("8/p7/1P6/8/8/8/p7/8 b - -")
        val player = Player("Black", board, Color.BLACK)
        assertEquals(false, player.gameFinished(board))

        // when we make the move
        val move = Move.createMove(Square.A7, Square.B6)

        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
            board.printBoard()
        } else assertFalse(true)

        // then we check that black wins with correct leaveMessage

        assertEquals(true, player.gameFinished(board,GameState.HASWON))
        assertEquals(true, player.gameFinished(board))
        assertEquals("Black Wins!\nBye!", player.leaveMessage)

    }

    @Test
    fun `test stalemate if we cannot move`() {

        // given a board with a stalemate after one black move
        var board = BoardFactory.getBoard("8/8/8/8/p7/8/P7/8 b - -")
        board.printBoard()

        // when we make the move
        var move = Move.createMove(Square.A4, Square.A3)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
            board.printBoard()
        }

        // then the game ends as stalemate and we check the leavemessage

        var player = Player("WHITE", board, Color.WHITE)
        assertEquals(true, player.gameFinished(board))
        assertEquals(true, player.gameFinished(board,GameState.GAMEDRAWN))
        assertEquals("Stalemate!\nBye!", player.leaveMessage)
    }
    @Test
    fun `test stalemate for white cannot move`() {

        // given a board with a stalemate after one black move
        var board = BoardFactory.getBoard("8/8/8/8/p7/8/P7/8 w - -")
        board.printBoard()

        // when we make the move
        var move = Move.createMove(Square.A2, Square.A3)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
            board.printBoard()
        }

        // then the game ends as stalemate and we check the leavemessage

        var player = Player("BLACK", board, Color.BLACK)
        assertEquals(true, player.gameFinished(board,GameState.GAMEDRAWN))
 //       assertEquals(true, player.gameFinished(board,GameState.GAMEDRAWN))
        assertEquals("Stalemate!\nBye!", player.leaveMessage)
    }*/
}