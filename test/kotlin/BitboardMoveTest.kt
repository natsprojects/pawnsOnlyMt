package pawnChess
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class BitboardMoveTest {

    @Test
    fun testPawnMove()  {
        assertNotEquals(BitboardMove.pawnMove(Color.WHITE, Bitboard.A2), Bitboard.A5)
        assertEquals(BitboardMove.pawnMove(Color.WHITE, Bitboard.A3), Bitboard.A4)
        assertEquals(BitboardMove.pawnMove(Color.BLACK, Bitboard.A2), Bitboard.A1)
        assertEquals(BitboardMove.pawnMove(Color.BLACK, Bitboard.A7), Bitboard.A6)
        assertEquals(
            BitboardMove.pawnDoubleMove(Color.WHITE, Bitboard.A2)
                xor Bitboard.A4, Bitboard.A4)
        assertEquals(BitboardMove.pawnDoubleMove(Color.WHITE, Bitboard.A3), Bitboard.A4)
        assertEquals(BitboardMove.pawnDoubleMove(Color.BLACK, Bitboard.A7), Bitboard.EMPTY)
        assertEquals(BitboardMove.pawnDoubleMove(Color.BLACK, Bitboard.A6), Bitboard.A5)
    }

    @Test
    fun testPawnAttack()  {
        assertEquals(BitboardMove.pawnAttacks(Color.WHITE, Bitboard.A2), Bitboard.B3)
        assertEquals(BitboardMove.pawnAttacks(Color.WHITE, Bitboard.H2), Bitboard.G3)

        assertEquals(BitboardMove.pawnAttacks(Color.BLACK, Bitboard.A2), Bitboard.B1)
        assertEquals(BitboardMove.pawnAttacks(Color.BLACK, Bitboard.H2), Bitboard.G1)

    }

}