import pawnChess.*
import kotlin.math.max

/**
 * Board factory.
 */
object BoardFactory {
    const val STARTER_FEN = "8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -"
    private const val SPLITTER = '/'

    private const val BOARD_POSITION = 0
    private const val COLOR_POSITION = 1

    private const val EP_SQUARE_POSITION = 3

    private const val MOVE_NUMBER_POSITION = 5

    /**
     * Get a board with starter position.
     */
    fun getBoard(): Board {
        return getBoard(STARTER_FEN)
    }

    /**
     * Get a board with a specific position.
     */
    fun getBoard(fen: String): Board {
        val result = Board()
        setBoard(fen, result)
        return result
    }

    /**
     * Change the board to a specific position.
     */
    fun setBoard(fen: String, board: Board) {
        board.reset()
        var square = Square.A8

        val tokenList = fen.split(' ')

        for (token in tokenList[BOARD_POSITION]) {
            if (token == SPLITTER) {
                square -= BitboardMove.DOUBLE_PAWN_FORWARD[Color.WHITE]
                continue
            }

            val rank = Rank.getRank(token)

            if (Rank.isValid(rank)) {
                square += rank + 1
            } else {
                val piece = Piece.getPiece(token)
                val pieceColor = Piece.getPieceColor(token)
                board.putPiece(pieceColor, piece, square)
                square += 1
            }
        }

        val colorToMove = Color.getColor(tokenList[COLOR_POSITION].single())

        val epSquare = Square.getSquare(tokenList[EP_SQUARE_POSITION])

        var moveNumber = 0


        moveNumber = max(Color.SIZE * (moveNumber - Color.BLACK), Color.WHITE) + colorToMove

        board.colorToMove = colorToMove
        board.nextColorToMove = Color.invertColor(colorToMove)

        board.epSquare = epSquare

        board.moveNumber = moveNumber
    }
}
