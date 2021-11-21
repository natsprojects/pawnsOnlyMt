package pawnChess

class Board {

    var gameBitboard = 0L
    var emptyBitboard = 0L
    var moveNumber = 0
    var moveType = MoveType.TYPE_NONE
    var colorToMove = Color.WHITE
    var nextColorToMove = Color.BLACK
    var pieceTypeBoard = IntArray(Square.SIZE)
    var pieceBitboard = Array(Color.SIZE) { LongArray(Piece.SIZE) }
    var epSquare = Square.NONE
    var errorMessage = ""
    var debug = false


    val evalInfo = EvalInfo()

    fun getMessage(): String {
        return errorMessage
    }

    /**
     * Reset the board information.
     */
    fun reset() {
        epSquare = Square.NONE
        gameBitboard = 0L
        emptyBitboard = 0L
        moveNumber = 0
        colorToMove = Color.WHITE
        nextColorToMove = Color.BLACK
        pieceBitboard = Array(Color.SIZE) { LongArray(Piece.SIZE) }

    }

    fun printBoard() {
        val buffer = StringBuilder()
        val sb = StringBuilder()
        sb.append("${Color.fullColor(this.colorToMove)} to move")
        println(sb.toString())

        val rowSep = "+---+---+---+---+---+---+---+---+"

        val w = "W"
        val b = "B"
        val whiteBitboard = pieceBitboard[Color.WHITE][Piece.PAWN]
        sb.append(Bitboard.toStringAsBits(whiteBitboard, prefix = "\nWhite Bitboard:"))
        val blackBitboard = pieceBitboard[Color.BLACK][Piece.PAWN]
        sb.append(Bitboard.toStringAsBits(blackBitboard, prefix = "\nBlack Bitboard:"))
        var prefix = ""
        var square: Int
        for (rank in Rank.SIZE - 1 downTo 0) {
            for (file in 0 until File.SIZE) {

                square = Square.getSquare(file, rank)
                if (file == 0) buffer.append("  \n  $rowSep\n${rank + 1} | ")
                if (square <= 64) prefix = " ${Square.toString(square)} (${rank + 1},${file + 1}) $square"
                if (whiteBitboard and Bitboard.getBitboard(square) != Bitboard.EMPTY) {
                    sb.append(
                        Bitboard.toStringAsBits(
                            Bitboard.getBitboard(square),
                            prefix = "\nWhite pawn: $prefix"
                        )
                    )
                    buffer.append("$w | ")
                } else {
                    if (blackBitboard and Bitboard.getBitboard(square) != Bitboard.EMPTY) {
                        sb.append(
                            Bitboard.toStringAsBits(
                                Bitboard.getBitboard(square),
                                prefix = "\nBlack pawn: $prefix"
                            )
                        )
                        buffer.append("$b | ")
                    } else buffer.append("  | ")
                }
            }
        }
        buffer.append("  \n  $rowSep\n ")
        buffer.append("    a   b   c   d   e   f   g   h\n")
        if (debug) println(sb.toString())
        println(buffer.toString())
    }


    /**
     * Execute a movement, need to check if move is legal before making it.
     */
    fun doMove(move: Int) {

        if (moveType == MoveType.TYPE_NONE) moveType = MoveGen.generateLegalMove(board = this, move = move)
        val fromSquare = Move.getFromSquare(move)
        val toSquare = Move.getToSquare(move)

        val ourColor = colorToMove
        val theirColor = nextColorToMove

        var capturedSquare = toSquare

        when (moveType) {
            MoveType.TYPE_PASSANT -> {
                capturedSquare -= BitboardMove.PAWN_FORWARD[ourColor] + toSquare
                val neighbour = if (pieceTypeBoard[fromSquare - 1] == Piece.PAWN) -1 else 1
                movePiece(ourColor, fromSquare, toSquare)
                removePiece(theirColor, fromSquare + neighbour)
                errorMessage = "enPassant capture"
            }
            MoveType.TYPE_CAPTURE -> {
                movePiece(ourColor, fromSquare, toSquare)
                removePiece(theirColor, capturedSquare)
                pieceTypeBoard[capturedSquare] = Piece.NONE
                errorMessage = "Capture"
            }
            MoveType.TYPE_NORMAL_ONE, MoveType.TYPE_NORMAL_TWO -> {
                movePiece(ourColor, fromSquare, toSquare)
                errorMessage = "Pawn push"
            }
        }

        clearEpSquare()
        if (fromSquare xor toSquare == 16 &&
            BitboardMove.NEIGHBOURS[toSquare] and pieceBitboard[theirColor][Piece.PAWN] != 0L
        ) {
            epSquare = toSquare xor 8
        }
        colorToMove = theirColor
        nextColorToMove = ourColor
        moveNumber++
        moveType = MoveType.TYPE_NONE

    }

/*

    fun undoMove(move: Int) {
        nextColorToMove = colorToMove
        colorToMove = Color.invertColor(colorToMove)
        val ourColor = colorToMove
        val theirColor = nextColorToMove
    }
*/

    private fun clearEpSquare() {
        epSquare = Square.NONE
    }

    private fun removePiece(color: Int, square: Int) {
        val bitboard = Bitboard.getBitboard(square)
        pieceBitboard[color][Piece.PAWN] = pieceBitboard[color][Piece.PAWN] xor bitboard

    }

    /**
     * Copy the object state from another board.
     */
    fun copy(board: Board) {
        gameBitboard = board.gameBitboard
        emptyBitboard = board.emptyBitboard
        moveNumber = board.moveNumber
        colorToMove = board.colorToMove
        nextColorToMove = board.nextColorToMove

        for (color in 0 until Color.SIZE) {
            for (i in 0 until Piece.SIZE) {
                pieceBitboard[color][i] = board.pieceBitboard[color][i]
            }
        }

        for (i in 0 until Square.SIZE) {
            pieceTypeBoard[i] = board.pieceTypeBoard[i]
        }

        epSquare = board.epSquare

    }

    /**
     * Insert a piece on the board.
     */
    fun putPiece(color: Int, piece: Int, square: Int) {
        val bitboard = Bitboard.getBitboard(square)
        pieceTypeBoard[square] = piece
        pieceBitboard[color][Piece.PAWN] = pieceBitboard[color][Piece.PAWN] or bitboard
        pieceBitboard[color][Piece.PAWN] = pieceBitboard[color][Piece.PAWN] or bitboard

    }

    private fun movePiece(color: Int, fromSquare: Int, toSquare: Int) {

        val moveBitboard = Bitboard.getBitboard(fromSquare) xor Bitboard.getBitboard(toSquare)
        pieceBitboard[color][Piece.PAWN] = pieceBitboard[color][Piece.PAWN] xor moveBitboard
        pieceBitboard[color][Piece.NONE] = pieceBitboard[color][Piece.NONE] xor moveBitboard
        pieceTypeBoard[fromSquare] = Piece.NONE
        pieceTypeBoard[toSquare] = Piece.PAWN
    }
}

