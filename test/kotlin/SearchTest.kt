package pawnChess

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


private fun getList(fen: String = "", depth: Int): List<Pair<Int, Int>> {
    val board = BoardFactory.getBoard(fen)
    board.printBoard()
    val search = Search(board, depth = depth)
    val sm = search.scoredMoves()
    sm.forEach { println("move: ${Move.toString(it.first)} ${it.second}") }
    return sm
}

internal class SearchTest {

    @Test
    fun `test for a draw`() {
        // given a board with two pawns in a drawn position

        val slist = getList("8/8/p7/8/P7/8/8/8 w - -", 6)

        // then the position is drawn
        assertEquals(DRAW, slist[0].second)

    }

    @Test
    fun `test for a draw - 2`() {
        // given a board with two pawns in a drawn position
        val board = BoardFactory.getBoard("8/8/p2p2p1/8/8/P2P2P1/8/8 w - -")

        val slist = getList("8/8/p2p2p1/8/8/P2P2P1/8/8 w - -", 4)

        // all white moves are drawn

        assertEquals(DRAW, slist[0].second)
        assertEquals(DRAW, slist[1].second)
        assertEquals(DRAW, slist[2].second)
    }

    @Test
    fun `test for a win-loss simple - 1 `() {
        // given a board with two pawns in a winning  position move for white

        val slist = getList("8/8/p7/1P6/8/8/8/8 w - -", 6)
        assertEquals(WHITE_WINS, slist[0].second)
        assertEquals(WHITE_WINS, slist[1].second)

    }

    @Test
    fun `test for a win-loss  simple - 2`() {
        // given a board with black to win next move or black to draw after wrong move and white to win in 1 moves

        val slist = getList("8/pp5P/8/8/P7/8/1p6/8 b - -", 6)
        assertEquals(BLACK_WINS, slist[0].second)

    }

    @Test
    fun `test for a win-loss  simple - 3`() {

        // given a board with white to win by a race to end of the board

        val slist = getList("8/ppp5/8/6PP/P7/8/8/8 b - -", 6)

        assertEquals(WHITE_WINS, slist[0].second)
        assertEquals(WHITE_WINS, slist[1].second)
        assertEquals(WHITE_WINS, slist[1].second)
    }

    @Test
    fun `test for a win-loss  simple - 4`() {
        // given a board with black to win next move or black to draw after wrong move and white to win in 2 moves
        val slist = getList("8/pp6/7P/8/P7/8/1p6/8 b - -", depth = 4)
        assertEquals(BLACK_WINS, slist[0].second)

    }

    @Test
    fun `test for a win-loss - 2`() {
        // given a board with black to win next move or black to draw after wrong move and white to win in 2 moves
        val slist = getList("8/pp6/7P/8/P7/8/1p6/8 b - -", 6)
        assertEquals(BLACK_WINS, slist[0].second)
        assertEquals(BLACK_WINS, slist[1].second)
    }

    @Test
    fun `test for a black win by capturing all pieces but loses if does not capture - 3`() {
        // given a board with black to win next move by taking all pieces of white

        var slist = getList("8/6p1/7P/8/8/8/8/8 b - -", depth = 4)
        assertEquals(BLACK_WINS, slist[0].second)
        assertEquals(WHITE_WINS, slist[1].second)
        assertEquals(WHITE_WINS, slist[2].second)
    }

    @Test
    fun `test a win-loss - 4`() {
        val slist = getList("8/pp6/8/8/2p1p3/6P1/P5PP/8 w - -", depth = 6)
        assertEquals(BLACK_WINS, slist[0].second)
        assertEquals(BLACK_WINS, slist[0].second)
        assertEquals(BLACK_WINS, slist[0].second)
    }

    @Test
    fun `test a win-loss`() {
        // given a a board where black must take pawn to win else lose

        var slist = getList("8/6p1/4p2P/8/p7/2PP3P/8/8 b - -", depth = 6)

        //then black wins
        assertEquals(BLACK_WINS, slist[0].second)
        //otherwise white wins
        assertEquals(WHITE_WINS, slist[1].second)


    }

    @Test
    fun `test a win-loss - tricky`() {

        // given a position where white has one move to win otherwise loses
        val slist = getList("8/pp6/8/6p1/8/8/P5PP/8 w - -", depth = 12)
        assertEquals(WHITE_WINS, slist[0].second)
        assertEquals(BLACK_WINS, slist[1].second)
        assertEquals(BLACK_WINS, slist[2].second)
        assertEquals(BLACK_WINS, slist[3].second)
    }

}

