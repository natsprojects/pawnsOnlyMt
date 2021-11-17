package pawnChess

import kotlinx.coroutines.*
class Search(board: Board, val prune: Int) {

    var position = Position(board)
    val ml = MoveGen.legalMovesScored(board, board.colorToMove)
    var move = 0
    private fun getList(fen: String = "", depth: Int = 5, prune: Int = 16, limit: Int = 1): List<Pair<Int, Int>> {
        val board = BoardFactory.getBoard(fen)
        board.printBoard()

        //when we  search a large depth to ensure we get an end game condition -  1 ply
        val search = Search(board, prune)
        val sm = search.scoredMoves(depth, limit = limit)
        sm.forEach { println("move: ${Move.toString(it.first)} ${it.second}") }
        return sm
        }
 //   suspend fun differentThread () = withContext(Dispatchers.Default)
    fun scoredMoves(depth: Int, limit: Int = 1): List<Pair<Int, Int>> {
        val p = Position(board = position.board)

        val isWhite = position.board.colorToMove == Color.WHITE
        var mlist = mutableListOf<Pair<Int,Int>>()

        with(p) {
            val ml = MoveGen.legalMovesScored(board,board.colorToMove)
            val oldBoard = BoardFactory.getBoard()
            oldBoard.copy(board)
            for (move in ml) {
                if (MoveGen.isLegalMove(board, move.first)) {
                    board.doMove(move.first)
 //                   println("Making move: ${Move.toString(move.first)}")
//                    board.printBoard()
                    val minmax = minMax(depth, !isWhite)
                    mlist.add(Pair(move.first, minmax.second))
                    board.copy(oldBoard)
                }
            }
            if (board.colorToMove == Color.WHITE) {
                mlist.sortByDescending { it.second }
            } else {
                mlist.sortBy { it.second }
            }
        }
        return mlist
    }


    fun minMax(depth: Int, whitePlayer: Boolean): Pair<Int?, Int> {

        with(position) {

            var ourColor = if (whitePlayer) Color.WHITE else Color.BLACK

            if (depth == 0 || gameStateEnd(GameState.PLAYING, ourColor)) {
                return Pair<Int?, Int>(null, evaluate(whitePlayer))
            }
            var minOrMax: Pair<Int?, Int> = Pair(null, if (whitePlayer) Int.MIN_VALUE else Int.MAX_VALUE)

            val sortedMoves = MoveGen.legalMovesScored(board, board.colorToMove)
            //       position.setPrune(70)
 //           if (state != GameState.PLAYING) return Pair(Move.NONE, 0)

            for (move in sortedMoves) {
                var oldBoard: Board = Board()
                oldBoard.copy(board)

                if (!MoveGen.isLegalMove(board,move.first)) {
                    println("Bad move")
                } else {
                    board.doMove(move.first)
                }

                val child = minMax(depth - 1, !whitePlayer)
                val score = Pair(move.first, child.second)
                board.copy(oldBoard)
                val m = minOrMax.first?.let { Move.toString(it) }
                if ((whitePlayer && score.second > minOrMax.second) || (!whitePlayer && score.second < minOrMax.second)) {

                    minOrMax = score
                }
            }

        return minOrMax
        }
    }

}

