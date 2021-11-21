package pawnChess
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class MoveTest {

    @Test
    fun testFrom() {
        assertEquals(
            Move.getFromSquare(Move.createMove(Square.E2, Square.E4, Piece.PAWN)),
            Square.E2
        )
    }

    @Test
    fun testTo() {
        assertEquals(Move.getToSquare(Move.createMove(Square.E2, Square.E4, Piece.PAWN)), Square.E4)
    }

    @Test
    fun testMoveType() {
        assertEquals(
            Move.getMoveType(Move.createMove(Square.E4, Square.F3, MoveType.TYPE_PASSANT)),
            MoveType.TYPE_PASSANT
        )
    }


    @Test
    fun testGetMove() {
        val board = BoardFactory.getBoard("8/8/8/3Pp3/8/8/8/8 w - e6")

        val m1 = Move.getMove( "d5e6")

        val m2 = Move.createMove(Square.D5, Square.E6)

        assertEquals(
            m1, m2
        )
    }


}