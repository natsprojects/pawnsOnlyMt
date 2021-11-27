package pawnChess

const val ONESQUARE = 8
const val TWOSQUARE = 16
const val RIGHT = 1
const val LEFT = -RIGHT

const val RANK_TWO_MASK = 0xFFL shl ONESQUARE
const val RANK_SEVEN_MASK = 0xFFL shl ONESQUARE * 6
object GameConstants {

    val COLOR_FACTOR = intArrayOf(1, -1)

    const val GAME_MAX_LENGTH = 1024

    const val MAX_MOVES = 4096
    const val MAX_PLIES = 128
    const val MAX_PLY_MOVES = 256

}
