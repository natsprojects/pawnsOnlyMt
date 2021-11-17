

package pawnChess
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*


class BoardFactoryTest {

    @Test
    fun testGetBoard() {
        fun testGetBoard() {
            val board = BoardFactory.getBoard(BoardFactory.STARTER_FEN)
            assertEquals(board.pieceBitboard[Color.WHITE][Piece.NONE], Bitboard.RANK_1 or Bitboard.RANK_2)
    //        kotlin.test.assertEquals(board.pieceBitboard[Color.BLACK][Piece.NONE], Bitboard.RANK_7 or Bitboard.RANK_8)

            assertEquals(board.pieceBitboard[Color.WHITE][Piece.PAWN], Bitboard.RANK_2)
   //         kotlin.test.assertEquals(board.pieceBitboard[Color.BLACK][Piece.PAWN], Bitboard.RANK_7)

            assertEquals(board.epSquare, Square.NONE)

            assertEquals(board.colorToMove, Color.WHITE)
        }
    }

}


