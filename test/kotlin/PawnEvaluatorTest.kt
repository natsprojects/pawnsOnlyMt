package pawnChess
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class PawnEvaluatorTest {

    val attackInfo = AttackInfo()

    private fun evaluate(fen: String): Int {
        val board = BoardFactory.getBoard(fen)
        board.printBoard()
        return PawnEvaluator.evaluate(board, AttackInfo())
    }

    @Test
    fun testPawnEquals1() {
        // given a board with blocked pieces
        val fen = "8/8/8/pp6/8/8/PP6/8 b - -"
        val board = BoardFactory.getBoard(fen)

        // when we evaluate the position
        val eval = PawnEvaluator.evaluate(board, AttackInfo())


        // then the position evaluates zero
        println("eval $eval")
        assertTrue(eval == 0)
    }

    @Test
    fun testPawnAdvantage1() {
        val eval = evaluate("8/4p3/8/8/8/8/PPPPPPPP/8 b - -")
        println("eval $eval")
        assertTrue(eval > 0)
    }

    @Test
    fun testPawnAdvantage2() {
        val eval = evaluate("8/ppp5/8/8/8/7P/3P4/8 b - -")

        println("eval $eval")
        assertTrue(eval < 0)
    }

    @Test
    fun testPawnAdvantage3() {
        val eval = evaluate("8/ppp5/8/8/8/8/PP6/8 b - -")

        println("eval $eval")
        assertTrue(eval < 0)
    }


}