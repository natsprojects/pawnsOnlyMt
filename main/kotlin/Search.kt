package pawnChess

import kotlin.math.*
class Search(board: Board, val depth: Int) {

    var position = Position(board)
    var move = 0

    fun scoredMoves(): List<Pair<Int, Int>> {
        val p = Position(board = position.board)

        val isWhite = position.board.colorToMove == Color.WHITE
        val mlist = mutableListOf<Pair<Int, Int>>()

        with(p) {
            val ml = MoveGen.legalMovesScored(board, board.colorToMove)
            val oldBoard = BoardFactory.getBoard()
            oldBoard.copy(board)
            var alpha = Int.MIN_VALUE
            var beta = Int.MAX_VALUE
            var nMoves = ml.size shl 2
            nMoves  = depth  * 80 / nMoves
            nMoves = max(nMoves,depth)
            nMoves = min(nMoves, 20)
            for (move in ml) {
                if (MoveGen.isLegalMove(board, move.first)) {
                    board.doMove(move.first)
                    //                   println("Making move: ${Move.toString(move.first)}")
 //                   board.printBoard()
                    val minmax = minMaxAlphaBeta(nMoves, !isWhite,alpha,beta)
//                    val minmax = minMax(depth, !isWhite)
                    mlist.add(Pair(move.first, minmax.second))
                    if (isWhite) {
 //                       alpha = max(alpha,minmax.second)
                    } else {
  //                      beta = min (beta, minmax.second)
                    }
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

            val ourColor = if (whitePlayer) Color.WHITE else Color.BLACK

            if (depth == 0 || gameStateEnd(GameState.PLAYING, ourColor)) {
                return Pair<Int?, Int>(null, evaluate(whitePlayer))
            }
            var minOrMax: Pair<Int?, Int> = Pair(null, if (whitePlayer) Int.MIN_VALUE else Int.MAX_VALUE)

            val sortedMoves = MoveGen.legalMovesScored(board, board.colorToMove)
            //       position.setPrune(70)
            //           if (state != GameState.PLAYING) return Pair(Move.NONE, 0)

            for (move in sortedMoves) {
                val oldBoard = Board()
                oldBoard.copy(board)
                if (!MoveGen.isLegalMove(board, move.first)) {
 //                   println("Bad move")
                } else {
                    board.doMove(move.first)
                }
                val child = minMax(depth - 1, !whitePlayer)
                val score = Pair(move.first, child.second)
                board.copy(oldBoard)
                if ((whitePlayer && score.second > minOrMax.second) || (!whitePlayer && score.second < minOrMax.second)) {
                    minOrMax = score
                }
            }

            return minOrMax
        }
    }


    fun minMaxAlphaBeta(depth: Int, whitePlayer: Boolean,alpha: Int, beta: Int): Pair<Int?, Int> {
        var alpha = alpha
        var beta = beta
        with(position) {

            val ourColor = if (whitePlayer) Color.WHITE else Color.BLACK

            if (depth == 0 || gameStateEnd(GameState.PLAYING, ourColor)) {
                return Pair<Int?, Int>(null, evaluate(whitePlayer))
            }
            var minOrMax: Pair<Int?, Int> = Pair(null, if (whitePlayer) Int.MIN_VALUE else Int.MAX_VALUE)
            val sortedMoves = MoveGen.legalMovesScored(board, board.colorToMove)

            for (move in sortedMoves) {

                val oldBoard = Board()
                oldBoard.copy(board)
                if (!MoveGen.isLegalMove(board, move.first)) {
                } else {
                    board.doMove(move.first)
                }
                val child = minMaxAlphaBeta(depth - 1, !whitePlayer,alpha,beta)
                val score = Pair(move.first, child.second)
                board.copy(oldBoard)
                if (whitePlayer && score.second > minOrMax.second)  {
                    minOrMax = score
                    alpha = max(alpha,minOrMax.second)
                    if (beta <= alpha)
                        break
                }
                if (!whitePlayer && score.second < minOrMax.second){
                    minOrMax = score
                    beta = min(beta,minOrMax.second)
                    if (beta <= alpha)
                        break
                }
            }

            return minOrMax
        }
    }

}

