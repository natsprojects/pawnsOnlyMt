
package pawnChess

class AttackInfo {

    var attacksBitboard = Array(Color.SIZE) { LongArray(Piece.SIZE) }
        private set

    var pieceMovement = Array(Color.SIZE) { LongArray(Square.SIZE) }
        private set

    var movementMask = LongArray(Color.SIZE)

    fun update(board: Board, color: Int) {
        for (color in 0 until Color.SIZE) { pieceMovement[color].fill(0,Square.A1,Square.SIZE) }
        pieceMovement[color]
        attacksBitboard[color].fill(0L,Square.A1,Square.SIZE)
      /*  val checkBitboard = board.basicEvalInfo.checkBitboard*/

        val mask = Bitboard.ALL

        if (mask != Bitboard.EMPTY) {
            pawnAttacks(board, color, mask)
        }
        movementMask[color] = mask
        attacksBitboard[color][Piece.NONE] = attacksBitboard[color][Piece.PAWN]

    }

    private fun pawnAttacks(board: Board, color: Int, mask: Long) {
        val unpinnedPawns = board.pieceBitboard[color][Piece.PAWN]
        attacksBitboard[color][Piece.PAWN] = BitboardMove.pawnAttacks(color, unpinnedPawns) and mask

        var tmpPieces = board.pieceBitboard[color][Piece.PAWN]

        while (tmpPieces != Bitboard.EMPTY) {
            // TODO check this is ok Square.getSquare(tmpPieces)
            val fromSquare = Square.getSquare(tmpPieces)
            val fromBitboard = Bitboard.getBitboard(fromSquare)
            val bitboard = BitboardMove.pawnAttacks(color, fromBitboard) and mask

            attacksBitboard[color][Piece.PAWN] = attacksBitboard[color][Piece.PAWN] or bitboard
            tmpPieces = tmpPieces and tmpPieces - 1
        }
    }

}
