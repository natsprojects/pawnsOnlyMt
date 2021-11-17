package pawnChess


class Position(var board: Board, var ourColor: Int = board.colorToMove) {
    var state = GameState.PLAYING
    var gameOver = false

    var leaveMessage = ""

    private fun hasWon(ourColor: Int): Int {

        val pieceBitboard = board.pieceBitboard[ourColor][Piece.PAWN]
        val theirBitboard = board.pieceBitboard[Color.invertColor(ourColor)][Piece.PAWN]
        state = if (pieceBitboard == Bitboard.EMPTY) GameState.HASLOST else state
        state = if (theirBitboard == Bitboard.EMPTY) GameState.HASWON else state

        when (ourColor) {
            Color.WHITE ->
                if ((theirBitboard and Bitboard.RANK_1 != Bitboard.EMPTY)) {
                    state = GameState.HASLOST
                } else {
                    if ((pieceBitboard and Bitboard.RANK_8 != Bitboard.EMPTY)) {
                        state = GameState.HASWON
                    }
                }
            Color.BLACK -> if ((theirBitboard and Bitboard.RANK_8 != Bitboard.EMPTY)) {
                state = GameState.HASLOST
            } else {
                if ((pieceBitboard and Bitboard.RANK_1 != Bitboard.EMPTY)) {
                    state = GameState.HASWON
                }
            }
        }
        leaveMessage = if (state == GameState.HASWON) "${Color.fullColor(ourColor)} Wins!\nBye!" else "Bye!"
        if (state == GameState.HASWON || state == GameState.HASLOST) {
   //         println(" ${Color.fullColor(ourColor)}: ${GameState.toString(state)} ... in hasWon")
            gameOver = true

        }
        return state
    }

    private fun hasDrawn(ourColor: Int): Int {
        //     if (state == GameState.GAMEDRAWN) return GameState.GAMEDRAWN
        val legalMoves = MoveGen.generateLegalMoves(board, ourColor = ourColor)
        if (legalMoves.isEmpty()) {
            state = GameState.GAMEDRAWN
            leaveMessage = "Stalemate!\nBye!"
 //           println(leaveMessage)
        }
        return state
    }

    private var moves = OrderedMoveList()

    fun evaluate(whitePlayer: Boolean): Int {
        ourColor = if (whitePlayer) Color.WHITE else Color.BLACK

        var score = when (state) {
            GameState.HASWON -> if (whitePlayer) Int.MAX_VALUE else Int.MIN_VALUE
            GameState.HASLOST -> if (whitePlayer) Int.MIN_VALUE else Int.MAX_VALUE
            GameState.PLAYING -> if (whitePlayer) PawnEvaluator.evaluate(board, AttackInfo())
            else -PawnEvaluator.evaluate(board, AttackInfo())
            GameState.GAMEDRAWN -> 0
            else -> {
                assert(false)
                0
            }
        }
        state = GameState.PLAYING
        return score
    }

    private fun hasResigned(resigns: Boolean = false): Int {
        if (resigns) {
            state = GameState.RESIGNED
        }
        return state
    }

    fun gameStateEnd(condition: Int, whitePlayer: Boolean): Boolean {
        ourColor = if (whitePlayer) Color.WHITE else Color.BLACK
        return gameStateEnd(condition, ourColor)
    }

    fun gameStateEnd(condition: Int, ourColor: Int): Boolean {
        when (condition) {
            GameState.HASWON -> state = hasWon(ourColor)
            GameState.GAMEDRAWN -> state = hasDrawn(ourColor)
            GameState.RESIGNED -> state = hasResigned()
            GameState.PLAYING -> {
                state = hasDrawn(ourColor)
                state = hasWon(ourColor)
                state = hasResigned()
            }
            else -> {
                state = GameState.ERROR
                leaveMessage = "Bye!"
            }
        }
        if (state != GameState.PLAYING) {
            println("end position reached: ${GameState.toString(state)} ${Color.fullColor(board.colorToMove)}")
            //   assert(false)
            return true
        }
        return false
    }


}