package pawnChess

import kotlinx.coroutines.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
//import java.util.concurrent.Flow
import java.util.concurrent.atomic.AtomicLong
import kotlin.coroutines.CoroutineContext
import  kotlinx.coroutines.*
import  kotlinx.coroutines.flow.*
import java.math.BigInteger
import java.util.*

private fun getList(fen: String = "", depth: Int): List<Pair<Int, Int>> {
    val board = BoardFactory.getBoard(fen)
    board.printBoard()

    //when we  search a large depth to ensure we get an end game condition -  1 ply
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
        assertEquals(0, slist[0].second)
        //     assertEquals(0,slist[1])
    }

    @Test
    fun `test for a draw - 2`() {
        // given a board with two pawns in a drawn position
        val board = BoardFactory.getBoard("8/8/p2p2p1/8/8/P2P2P1/8/8 w - -")

        val slist = getList("8/8/p2p2p1/8/8/P2P2P1/8/8 w - -", 4)
        assertEquals(0, slist[0].second)
        assertEquals(0, slist[1].second)
        assertEquals(0, slist[2].second)
    }

    @Test
    fun `test for a win-loss simple - 1 `() {
        // given a board with two pawns in a winning  position move for white
        val slist = getList("8/8/p7/1P6/8/8/8/8 w - -", 6)
        assertEquals(Int.MAX_VALUE, slist[0].second)
        assertEquals(Int.MAX_VALUE, slist[1].second)

    }

    @Test
    fun `test for a win-loss  simple - 2`() {
        // given a board with black to win next move or black to draw after wrong move and white to win in 1 moves
        val slist = getList("8/pp5P/8/8/P7/8/1p6/8 b - -", 6)
        assertEquals(Int.MIN_VALUE, slist[0].second)

    }

    @Test
    fun `test for a win-loss  simple - 3`() {
        // given a board with black to win next move or white to win in 2 moves
        val slist = getList("8/ppp5/8/6PP/P7/8/8/8 b - -", 6)
        //       assertEquals(Int.MAX_VALUE,slist[0].second)

    }

    @Test
    fun `test for a win-loss  simple - 4`() {
        // given a board with black to win next move or black to draw after wrong move and white to win in 1 moves
        val slist = getList("8/pp6/7P/8/P7/8/1p6/8 b - -", depth = 4)
        assertEquals(Int.MIN_VALUE, slist[0].second)

    }

    @Test
    fun `test for a win-loss - 2`() {
        // given a board with black to win next move or black to draw after wrong move and white to win in 2 moves
        val slist = getList("8/pp6/7P/8/P7/8/1p6/8 b - -", 6)
        assertEquals(Int.MIN_VALUE, slist[0].second)
        assertEquals(Int.MIN_VALUE, slist[1].second)

        //       assertEquals(Int.MAX_VALUE, slist[2].second)

    }

    @Test
    fun `test for a win by capturing all pieces - 3`() {
        // given a board with black to win next move by taking all pieces of white

        var slist = getList("8/6p1/7P/8/8/8/8/8 b - -", depth = 4)
        assertEquals(Int.MIN_VALUE, slist[0].second)
        assertEquals(Int.MAX_VALUE, slist[1].second)
        assertEquals(Int.MAX_VALUE, slist[2].second)
    }

    @Test
    fun `test a win-loss - 4`() {
        val slist = getList("8/pp6/8/8/2p1p3/6P1/P5PP/8 w - -", depth = 6)
        assertEquals(Int.MIN_VALUE, slist[0].second)
        assertEquals(Int.MIN_VALUE, slist[0].second)
        assertEquals(Int.MIN_VALUE, slist[0].second)
    }

    @Test
    fun `test we can  search a board with 3 pawns - 2 for black`() {
        // given a board with two black pawns black gets two moves after every white move

        var slist = getList("8/8/4p2P/8/8/2PP3P/8/8 w - -", depth = 10)
        assertEquals(Int.MIN_VALUE, slist[0].second)
//       assertEquals(Int.MIN_VALUE, slist[1].second)
        //       assertEquals(0, slist[2].second)

    }


    @Test
    fun `test complex - draw`() {
        val slist = getList("8/pp6/7P/8/6P1/8/P5PP/8 w - -", depth = 8)
        assertEquals(0, slist[0].second)
        assertEquals(0, slist[0].second)
        assertEquals(0, slist[0].second)
    }

    @Test
    fun `test a win-loss - 3`() {
        val slist = getList("8/pp6/8/6p1/8/8/P5PP/8 w - -", depth = 14)
        assertEquals(0, slist[0].second)
        assertEquals(0, slist[0].second)
        assertEquals(0, slist[0].second)
    }

    @Test
    fun `test a win-loss - 5`() {
        val slist = getList("8/pp6/6pp/3p4/PPPP4/4p3/P5PP/8 w - -", depth =14 )
        assertEquals(0, slist[0].second)
        assertEquals(0, slist[0].second)
        assertEquals(0, slist[0].second)
    }
    @Test
    fun `test we can  search a  board starter position`() {

        val slist = getList(fen = "8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -", depth = 8)

    }
}

/*
    @Test
    fun `do some adding`() {
        runBlocking<Unit> {
           println( addToList())
        }
    }

}

suspend fun addToList(): Pair<Int,Int> = coroutineScope {
    val one = async { doSomeThing1()  }
   // val two = async { doSomeThing2()  }
   Pair( one.await(), two.await())
}

suspend fun doSomeThing1(): Int {
   // delay(1000)
    return 10
}*/
/*suspend fun doSomeThing2(): Int {
  //  delay(1000)
    return 20
}*/
