import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import pawnChess.*

internal class OrderedMoveListTest {

    @Test
    fun getBestScored() {
    }
/*
    @Test
    fun range() {
        //given a board with no moves for white
        val board = BoardFactory.getBoard("8/8/7p/7P/8/8/8/8 w - -")

        // when we load the move
        val ml = MoveGen.legalMovesScored(board)

        // then  the list range should be 0..-1
        assertEquals(0..-1, ml.range())

        //given a board with one move for white
        val board2 = BoardFactory.getBoard("8/8/7p/8/8/P7/8/8 w - -")

        // when we load the move
        val ml2 = MoveGen.legalMovesScored(board2)

        // then  the list range is 0..0
        assertEquals(0..0, ml2.range())

        // given a board from the initial position with 16 moves and an ordered move list ml
        val board3 = BoardFactory.getBoard()

        // when we load the moves
        val ml3 = MoveGen.legalMovesScored(board3)


        // the list size is 16
        assertEquals(16, ml3.size())

        // and the list range is 0..15
        assertEquals(0..15, ml3.range())
    }

    @Test
    fun size() {
        //given a board with no moves for white
        val board = BoardFactory.getBoard("8/8/7p/7P/8/8/8/8 w - -")

        // when we load the move
        val ml = MoveGen.legalMovesScored(board)


        // then the size of the list should be 0
        assertEquals(0, ml.size())

        //given a board with one move for white
        val board2 = BoardFactory.getBoard("8/8/7p/8/8/P7/8/8 w - -")

        // when we load the move
        val ml2 = MoveGen.legalMovesScored(board2)


        // then the size of the list is 1
        assertEquals(1, ml2.size())
    }

    @Test
    fun prune() {
        // given a board from the initial position with 16 moves and an ordered move list ml
        val board = BoardFactory.getBoard()
        val ml = MoveGen.legalMovesScored(board)

        // when we set a prune down to  only 6 moves in the list
        ml.setPrune(6)

        // the list has a size of 6
        assertEquals(6, ml.size())

        // and a range 0..5
        assertEquals(0..5, ml.range())
    }

    @Test
    fun hasNext() {
        // given a board from the initial position with 16 white moves
        val board = BoardFactory.getBoard()
        val ml = MoveGen.legalMovesScored(board)

        // when we prune the list to 10 moves
        ml.setPrune(10)

        //then hasNext should iterate 10 moves
        var count = 0
        var move = 0
        while (ml.hasNext()) {
            move = ml.next()
            count++
        }
        assertEquals(10, count)
    }


    @Test
    fun next() {
        // given a board from the initial position with 16 white moves
        val board = BoardFactory.getBoard("8/1p2p3/p6P/p3pp2/2P5/7P/PPPP3/8 w - -")
        val ml = MoveGen.legalMovesScored(board)
        board.printBoard()
        // when we prune the list to 10 moves
  //      ml.setPrune(0)

        var count = 0
        var move = 0
        while (ml.hasNext()) {
            move = ml.next()
            count++
            println("$count ${Move.toString(move)} ${PawnEvaluator.evaluate(board, AttackInfo())}")
        }

        //then hasNext should count should be 10 moves
        assertEquals(16, count)
    }
    @Test
    fun nextByColor() {
        // given a board from the initial position with 16 white moves
        val board = BoardFactory.getBoard("8/1p2p3/p6P/p3pp2/2P5/7P/PPPP3/8 w - -")
        val ml = MoveGen.legalMovesScored(board)
        board.printBoard()
        // when we prune the list to 10 moves
        //      ml.setPrune(0)

        var count = 0
        var move = 0
        while (ml.hasNext()) {
            move = ml.next()
            count++
            println("$count ${Move.toString(move)} ${PawnEvaluator.evaluate(board, AttackInfo())}")
        }

        //then hasNext should count should be 10 moves
        assertEquals(16, count)
    }*/
}
