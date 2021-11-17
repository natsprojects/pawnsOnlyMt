package pawnChess
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
class BitboardTest {

    @Test
    fun testGetBitboard() {
        var bitboard:Long
        for (square in Square.A1 until Square.SIZE) {
             bitboard = Bitboard.getBitboard(square)

            assertEquals(bitboard, 1L shl square)
        }

    }
    @Test
    fun testPrintBitboard() {
        for (square in Square.A1 until Square.SIZE) {
            val bitboard = Bitboard.getBitboard(square)
 //          println( Bitboard.toPieceString(bitboard,Color.WHITE))
            assertEquals(bitboard, 1L shl square)
//       println(bStr((1L shl square).toInt()))
        }


    }


    @Test
    fun testOneElement() {
        for (square in Square.A1 until Square.SIZE) {
            val bitboard = Bitboard.getBitboard(square)
            assertTrue(Bitboard.oneElement(bitboard))
            val garbageBitboard = bitboard or Bitboard.getBitboard((square + 1) % Square.SIZE)
            assertFalse(Bitboard.oneElement(garbageBitboard))
        }
        assertFalse(Bitboard.oneElement(Bitboard.ALL))
    }
}

