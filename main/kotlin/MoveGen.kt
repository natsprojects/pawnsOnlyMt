package pawnChess
object MoveGen {

    fun isLegalMove(board: Board, move: Int): Boolean {
        val moveType = generateLegalMove(move, board)
        return moveType != MoveType.INVALID && moveType != MoveType.TYPE_NONE
    }

    fun generateLegalMove(move: Int, board: Board): Int {
        with(board) {
            val toSquare = Move.getToSquare(move)
            val fromSquare = Move.getFromSquare(move)
            val toBitboard = Bitboard.getBitboard(toSquare)
            val fromBitboard = Bitboard.getBitboard(fromSquare)
            val epBitboard = Bitboard.getBitboard(epSquare)
            val ourBitboard = pieceBitboard[colorToMove][Piece.PAWN]
            val theirBitboard = pieceBitboard[nextColorToMove][Piece.PAWN]

            gameBitboard = ourBitboard or theirBitboard

            moveType = MoveType.TYPE_NONE
      //      println( Bitboard.toStringAsBits(BitboardMove.pawnAttacks(colorToMove, fromBitboard)))
      //      println( Bitboard.toStringAsBits(toBitboard))
            val pawnAttackMoves =
                BitboardMove.pawnAttacks(colorToMove, fromBitboard) and toBitboard
            val pawnNeighbours = BitboardMove.populateNeighbour(colorToMove, fromBitboard, toSquare)

            // check we have a piece at from square
            if (fromBitboard and ourBitboard == Bitboard.EMPTY) {
                errorMessage = "no ${Color.fullColor(colorToMove)} pawn at ${Square.toString(fromSquare)}"
                moveType = MoveType.INVALID
                return moveType
            }

            moveType = MoveType.fetchMoveType(move, colorToMove)

            if (moveType == MoveType.INVALID || !Move.isValid(move)) {
                errorMessage = "Invalid Input"
                return moveType
            }
            val blockedBitboard = when (Rank.getRank(toSquare) - Rank.getRank(fromSquare)) {
                1 -> gameBitboard and (fromBitboard shl ONESQUARE)
                -1 -> gameBitboard and (fromBitboard ushr ONESQUARE)
                2 -> ((gameBitboard and (fromBitboard shl ONESQUARE)) shl ONESQUARE) or (gameBitboard and (fromBitboard shl TWOSQUARE))
                -2 -> ((gameBitboard and (fromBitboard ushr ONESQUARE)) ushr ONESQUARE) or (gameBitboard and (fromBitboard ushr TWOSQUARE))

                else -> Bitboard.ALL
            }

            errorMessage = "Invalid Input"
            when (moveType) {
                MoveType.TYPE_CAPTURE -> {
                    val captureBitboard =
                        pawnAttackMoves and theirBitboard                     // make sure we have their piece to capture
                    // make sure we have their piece to capture

                    if (captureBitboard == Bitboard.EMPTY) {
                        moveType =
                            if ((epBitboard and pawnAttackMoves) != Bitboard.EMPTY && pawnNeighbours != Bitboard.EMPTY) {
                                MoveType.TYPE_PASSANT
                            } else MoveType.INVALID
                    }
                }
                MoveType.TYPE_NORMAL_ONE -> {
                    val oneMove = toBitboard xor blockedBitboard
                    if (oneMove == Bitboard.EMPTY) moveType = MoveType.INVALID
                }
                MoveType.TYPE_NORMAL_TWO -> {
                    val twoMoveBitboard = arrayOf(
                        (RANK_TWO_MASK and fromBitboard) xor fromBitboard,
                        (RANK_SEVEN_MASK and fromBitboard) xor fromBitboard
                    )
                    val twoMove = twoMoveBitboard[colorToMove] xor blockedBitboard

                    if (twoMove != Bitboard.EMPTY) moveType = MoveType.INVALID
                }
                MoveType.TYPE_PASSANT -> {
                    val epCaptureBitboard = pawnAttackMoves and epBitboard
                    if (epCaptureBitboard != Bitboard.EMPTY) moveType = MoveType.INVALID
                }
            }

            return moveType
        }
    }

    fun legalMovesScoredOld( board: Board, ourColor: Int = board.colorToMove): OrderedMoveList {

        var legalMovesOurs = generateLegalMoves(board, ourColor)
        val orderedMoves = OrderedMoveList()
        var newBoard = Board()
        var eval = 0
        for (move in legalMovesOurs) {
                newBoard.copy(board)
                newBoard.doMove(move)
                eval = PawnEvaluator.evaluate(newBoard, AttackInfo())
                if (ourColor == Color.BLACK) eval *= -1
                orderedMoves.addMove(move = move, eval)
            }
        legalMovesOurs = listOf()
        return orderedMoves
        }

    fun legalMovesScored( board: Board, ourColor: Int = board.colorToMove):  List<Pair<Int,Int>>  {

        var legalMovesOurs = generateLegalMoves(board, ourColor)
        val orderedMoves = mutableListOf<Pair<Int,Int>>()
        var slist: MutableList<Pair<Int,Int>>
        var newBoard = Board()
        var eval = 0
        for (move in legalMovesOurs) {
            newBoard.copy(board)
            newBoard.doMove(move)
            eval = PawnEvaluator.evaluate(newBoard, AttackInfo())
            if (ourColor == Color.BLACK) eval *= -1
            orderedMoves.add(Pair(move, eval))
        }
        if (board.colorToMove == Color.WHITE) {
           orderedMoves.sortByDescending { it.second }
        } else {
            orderedMoves.sortBy { it.second }
        }
        return orderedMoves
    }
    fun generateLegalMoves(board: Board, ourColor: Int = board.colorToMove): List<Int> {
        // get our piece board
        val legalMoves = mutableListOf<Int>()
        with(board) {
            var ourBitboard = pieceBitboard[ourColor][Piece.PAWN]

            val allMoves = mutableListOf<Int>()

            // generate a list of all pieces from the bitboard

            val squareList = Square.getSquareListFromBitboard(ourBitboard)
            squareList.forEach { square ->
                    if (ourColor == Color.WHITE) {
                        allMoves.add(Move.createMove(square, square + ONESQUARE))
                        allMoves.add(Move.createMove(square, square + TWOSQUARE))
                        allMoves.add(Move.createMove(square, square + ONESQUARE + LEFT))
                        allMoves.add(Move.createMove(square, square + ONESQUARE + RIGHT))
                    } else {
                        allMoves.add(Move.createMove(square, square - ONESQUARE))
                        allMoves.add(Move.createMove(square, square - TWOSQUARE))
                        allMoves.add(Move.createMove(square, square - (ONESQUARE + LEFT)))
                        allMoves.add(Move.createMove(square, square - (ONESQUARE + RIGHT)))
                    }
                }

            // only want legal moves

            allMoves.forEach { move -> if (isLegalMove(board, move)) legalMoves.add(move) }

            return legalMoves
        }
    }

}