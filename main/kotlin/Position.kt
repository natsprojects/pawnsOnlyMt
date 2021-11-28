package pawnChess

class Position(var board: Board, var ourColor: Int = board.colorToMove) {
    var state = GameState.PLAYING

    var leaveMessage = GameState.getLeaveMessage(GameState.PLAYING, ourColor)

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

        return state
    }

    private fun hasDrawn(ourColor: Int): Int {

        val legalMoves = MoveGen.generateLegalMoves(board, ourColor = ourColor)
        if (legalMoves.isEmpty()) {
            state = GameState.GAMEDRAWN
        }
        return state
    }

    fun evaluate(whitePlayer: Boolean): Int {
        ourColor = if (whitePlayer) Color.WHITE else Color.BLACK

        val score = when (state) {
            GameState.HASWON -> if (whitePlayer) WHITE_WINS else BLACK_WINS
            GameState.HASLOST -> if (whitePlayer) BLACK_WINS else WHITE_WINS
            GameState.PLAYING -> if (whitePlayer) PawnEvaluator.evaluate(board, AttackInfo())
            else -PawnEvaluator.evaluate(board, AttackInfo())
            GameState.GAMEDRAWN -> DRAW
            else -> {
                assert(false)
                DRAW
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
            }
        }
        leaveMessage = GameState.getLeaveMessage(state, ourColor)
        return state != GameState.PLAYING
    }

    fun saveToFile() {

    }
}