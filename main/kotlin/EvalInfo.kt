
package pawnChess
class EvalInfo {

    val mobilityArea = LongArray(Color.SIZE)
    var passedPawnBitboard = Bitboard.EMPTY
    fun update(board: Board, attackInfo: AttackInfo) {
        attackInfo.update(board, Color.WHITE)
        attackInfo.update(board, Color.BLACK)
        mobilityArea[Color.WHITE] = (board.pieceBitboard[Color.WHITE][Piece.NONE] or
            attackInfo.attacksBitboard[Color.BLACK][Piece.PAWN]).inv()
        mobilityArea[Color.BLACK] = (board.pieceBitboard[Color.BLACK][Piece.NONE] or
            attackInfo.attacksBitboard[Color.WHITE][Piece.PAWN]).inv()
        passedPawnBitboard = Bitboard.EMPTY
    }

}