package pawnChess

object EvalConstants {

    const val SCORE_DRAW = 0

    const val SCORE_MAX: Int = Short.MAX_VALUE.toInt()
    const val SCORE_MIN: Int = -SCORE_MAX

    const val SCORE_MATE = SCORE_MAX - GameConstants.MAX_PLIES

    const val SCORE_UNKNOWN = Short.MIN_VALUE.toInt()

    const val SCORE_KNOW_WIN = 10000
 //   const    val SCORE_DRAWISH_MATERIAL = TunableConstants.MATERIAL_SCORE[Piece.PAWN]
    const val SCORE_DRAWISH_MATERIAL = 0
    val SCORE_LAZY_EVAL = SCORE_DRAWISH_MATERIAL * 10

    var PAWN_EVAL_CACHE = true

    val PAWN_SHIELD_MASK = LongArray(Square.SIZE)


    init {
        for (square in Square.A1 until Square.SIZE) {
            val file = File.getFile(square)
            val bitboard = Bitboard.getBitboard(square)
            PAWN_SHIELD_MASK[square] = Bitboard.FILES[file] or Bitboard.FILES_ADJACENT[file]

        }
    }
}