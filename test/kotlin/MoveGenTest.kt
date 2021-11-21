package pawnChess
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class MoveGenTest {

    @Test
    fun `test black has 16 legal moves`() {
        // given a player and a board
        var board = BoardFactory.getBoard("8/pppppppp/8/8/8/P7/1PPPPPPP/8 b - -")
        val position = Position(board)
        //       board.debug= true
        var legalMoves = MoveGen.generateLegalMoves(board, Color.BLACK)
        assertEquals(16, legalMoves.size)

        val player = Player("White", position , Color.WHITE)
        assertEquals(false, position.gameStateEnd(GameState.HASWON,position.board.colorToMove))
    }


 /*   @Test
    fun `test starter game position using move series`() {
        // given a player and a board
        var board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -")
        val position = Position(board)
        assertEquals(Color.WHITE, board.colorToMove)
        val player = Player("White", position , Color.WHITE)
        assertEquals(false, player1.gameFinished(board))

        var move = Move.createMove(Square.A2, Square.A4)
        if (MoveGen.isLegalMove(board, move)) {
            board.doMove(move)
        }
        var player2 = Player("Black", board, Color.BLACK)
        assertEquals(false, player2.gameFinished(board))
        assertEquals(Color.BLACK, board.colorToMove)
        board.printBoard()

        move = Move.createMove(Square.A7, Square.A5)
        if (MoveGen.isLegalMove(board, move)) {
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
    fun `test white wins by reaching 8th rank`() {

        // given a player and a board so white moves to the 8th rank
        var board = BoardFactory.getBoard("8/P7/1p6/8/8/8/8 w - -")

        val player = Player("White", board, Color.WHITE)
        assertEquals(false, player.gameFinished(board))
        val move = Move.createMove(Square.A7, Square.A8)

        // when we make a move
        if (MoveGen.isLegalMove(board, move)) {
            board.doMove(move)
            board.printBoard()
        } else assertFalse(true)

        // then white should win the game with correct leaveMessage

        assertEquals(true, player.gameFinished(board))
        assertEquals(true, player.gameFinished(board, GameState.HASWON))
        assertEquals("White Wins!\nBye!", player.leaveMessage)
    }

    @Test
    fun `test that a move can not be made if no piece of same color at from square is available`() {
        // given a board at starter and a setup for white to move
        val board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -")
        println(Bitboard.toStringAsBits(RANK_TWO_MASK))
        //when we make the move
        val move = Move.createMove(Square.B3, Square.B4)

        if (MoveGen.isLegalMove(board, move)) {
            board.doMove(move)
        }
        board.printBoard()
        // then we get the correct error message
        assertEquals("no White pawn at b3", board.errorMessage)

        // and its still whites turn
        assertEquals(board.colorToMove, Color.WHITE)

        // and pieces are still at starting places
        assertEquals(board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.B2, Bitboard.B2)
    }
*/
  /*  @Test
    fun `test we can generate correct number of 2 ply moves`() {
        // given a starter board,
        val board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -")
        var moves = MoveGen.legalMovesScored(board)
   //     println (moves.toString())
        assertEquals(16,moves.nextToGenerate)
        assertEquals(0,moves.nextToMove)
    }
    @Test
    fun `test we generate single pawn push moves`() {
        // given a starter board,
        val board = BoardFactory.getBoard("8/p7/8/7P//8/8/8 w - -")
        board.printBoard()
        var moves = MoveGen.legalMovesScored(board)
        //     println (moves.toString())
        assertEquals(1,moves.nextToGenerate)
        assertEquals(0,moves.nextToMove)
    }

    @Test
    fun `test we generate capture moves`() {
        // given a starter board,
        val board = BoardFactory.getBoard("8/p7/1P6/8/8/8/8/8 w - -")
        board.printBoard()
        var moves = MoveGen.legalMovesScored(board)
        //     println (moves.toString())
        assertEquals(2,moves.nextToGenerate)
        assertEquals(0,moves.nextToMove)
    }
    @Test
    fun `test we generate capture moves 2 pieces`() {
        // given a starter board,
        val board = BoardFactory.getBoard("8/p1p5/1P6/8/8/8/8/8 w - -")
        board.printBoard()
        var moves = MoveGen.legalMovesScored(board)
        //     println (moves.toString())
        assertEquals(3,moves.nextToGenerate)
        assertEquals(0,moves.nextToMove)
    }
    @Test
    fun `test attack gets correct number of moves`() {
        // given a starter board,
        val board = BoardFactory.getBoard("8/p7/1P6/8/8/8/8/8 w - -")
        board.printBoard()
        var moves = MoveGen.legalMovesScored(board)
        //     println (moves.toString())
        assertEquals(2,moves.size())
   //     assertEquals(0,moves.nextToMove)
    }*/
}
