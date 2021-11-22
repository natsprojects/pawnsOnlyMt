package pawnChess
// https://github.com/ratosh/pirarucu.git
import kotlin.math.abs
import kotlin.math.max

const val ONESQUARE = 8
const val TWOSQUARE = 16
const val RIGHT = 1
const val LEFT = -RIGHT
const val RANK_TWO_MASK = 0xFFL shl ONESQUARE
const val RANK_SEVEN_MASK = 0xFFL shl ONESQUARE * 6

/**
 * Bitboard singleton class.
 * Use a long to represent a chess board.
 */

object Bitboard {

    val A1 = getBitboard(Square.A1)
    val B1 = getBitboard(Square.B1)
    val C1 = getBitboard(Square.C1)
    val D1 = getBitboard(Square.D1)
    val E1 = getBitboard(Square.E1)
    val F1 = getBitboard(Square.F1)
    val G1 = getBitboard(Square.G1)
    val H1 = getBitboard(Square.H1)

    val A2 = getBitboard(Square.A2)
    val B2 = getBitboard(Square.B2)
    val C2 = getBitboard(Square.C2)
    val D2 = getBitboard(Square.D2)
    val E2 = getBitboard(Square.E2)
    val F2 = getBitboard(Square.F2)
    val G2 = getBitboard(Square.G2)
    val H2 = getBitboard(Square.H2)

    val A3 = getBitboard(Square.A3)
    val B3 = getBitboard(Square.B3)
    val C3 = getBitboard(Square.C3)
    val D3 = getBitboard(Square.D3)
    val E3 = getBitboard(Square.E3)
    val F3 = getBitboard(Square.F3)
    val G3 = getBitboard(Square.G3)
    val H3 = getBitboard(Square.H3)

    val A4 = getBitboard(Square.A4)
    val B4 = getBitboard(Square.B4)
    val C4 = getBitboard(Square.C4)
    val D4 = getBitboard(Square.D4)
    val E4 = getBitboard(Square.E4)
    val F4 = getBitboard(Square.F4)
    val G4 = getBitboard(Square.G4)
    val H4 = getBitboard(Square.H4)

    val A5 = getBitboard(Square.A5)
    val B5 = getBitboard(Square.B5)
    val C5 = getBitboard(Square.C5)
    val D5 = getBitboard(Square.D5)
    val E5 = getBitboard(Square.E5)
    val F5 = getBitboard(Square.F5)
    val G5 = getBitboard(Square.G5)
    val H5 = getBitboard(Square.H5)

    val A6 = getBitboard(Square.A6)
    val B6 = getBitboard(Square.B6)
    val C6 = getBitboard(Square.C6)
    val D6 = getBitboard(Square.D6)
    val E6 = getBitboard(Square.E6)
    val F6 = getBitboard(Square.F6)
    val G6 = getBitboard(Square.G6)
    val H6 = getBitboard(Square.H6)

    val A7 = getBitboard(Square.A7)
    val B7 = getBitboard(Square.B7)
    val C7 = getBitboard(Square.C7)
    val D7 = getBitboard(Square.D7)
    val E7 = getBitboard(Square.E7)
    val F7 = getBitboard(Square.F7)
    val G7 = getBitboard(Square.G7)
    val H7 = getBitboard(Square.H7)

    val A8 = getBitboard(Square.A8)
    val B8 = getBitboard(Square.B8)
    val C8 = getBitboard(Square.C8)
    val D8 = getBitboard(Square.D8)
    val E8 = getBitboard(Square.E8)
    val F8 = getBitboard(Square.F8)
    val G8 = getBitboard(Square.G8)
    val H8 = getBitboard(Square.H8)

    const val ALL: Long = -1
    const val EMPTY: Long = 0L
    const val WHITES: Long = -0x55aa55aa55aa55abL
    const val BLACKS: Long = 0x55aa55aa55aa55aaL

    val FILE_A = A1 or A2 or A3 or A4 or A5 or A6 or A7 or A8
    val FILE_B = B1 or B2 or B3 or B4 or B5 or B6 or B7 or B8
    val FILE_C = C1 or C2 or C3 or C4 or C5 or C6 or C7 or C8
    val FILE_D = D1 or D2 or D3 or D4 or D5 or D6 or D7 or D8
    val FILE_E = E1 or E2 or E3 or E4 or E5 or E6 or E7 or E8
    val FILE_F = F1 or F2 or F3 or F4 or F5 or F6 or F7 or F8
    val FILE_G = G1 or G2 or G3 or G4 or G5 or G6 or G7 or G8
    val FILE_H = H1 or H2 or H3 or H4 or H5 or H6 or H7 or H8

    val NOT_FILE_A = ALL xor FILE_A
    val NOT_FILE_B = ALL xor FILE_B
    val NOT_FILE_C = ALL xor FILE_C
    val NOT_FILE_D = ALL xor FILE_D
    val NOT_FILE_E = ALL xor FILE_E
    val NOT_FILE_F = ALL xor FILE_F
    val NOT_FILE_G = ALL xor FILE_G
    val NOT_FILE_H = ALL xor FILE_H

    val FILES = longArrayOf(FILE_A, FILE_B, FILE_C, FILE_D, FILE_E, FILE_F, FILE_G, FILE_H)

    val RANK_1 = A1 or B1 or C1 or D1 or E1 or F1 or G1 or H1
    val RANK_2 = A2 or B2 or C2 or D2 or E2 or F2 or G2 or H2
    val RANK_3 = A3 or B3 or C3 or D3 or E3 or F3 or G3 or H3
    val RANK_4 = A4 or B4 or C4 or D4 or E4 or F4 or G4 or H4
    val RANK_5 = A5 or B5 or C5 or D5 or E5 or F5 or G5 or H5
    val RANK_6 = A6 or B6 or C6 or D6 or E6 or F6 or G6 or H6
    val RANK_7 = A7 or B7 or C7 or D7 or E7 or F7 or G7 or H7
    val RANK_8 = A8 or B8 or C8 or D8 or E8 or F8 or G8 or H8

    val NOT_RANK_1 = ALL xor RANK_1
    val NOT_RANK_2 = ALL xor RANK_2
    val NOT_RANK_3 = ALL xor RANK_3
    val NOT_RANK_4 = ALL xor RANK_4
    val NOT_RANK_5 = ALL xor RANK_5
    val NOT_RANK_6 = ALL xor RANK_6
    val NOT_RANK_7 = ALL xor RANK_7
    val NOT_RANK_8 = ALL xor RANK_8

    val RANKS = longArrayOf(RANK_1, RANK_2, RANK_3, RANK_4, RANK_5, RANK_6, RANK_7, RANK_8)

    val FILES_ADJACENT = longArrayOf(
        FILE_B, FILE_A or FILE_C, FILE_B or FILE_D, FILE_C or FILE_E,
        FILE_D or FILE_F, FILE_E or FILE_G, FILE_F or FILE_H, FILE_G
    )

    /**
     * Get bitboard info from a square.
     */
    fun getBitboard(square: Int): Long {
        return 1L shl square
    }

    /**
     * Check if the bitboard only contains one element.
     */
    fun oneElement(bitboard: Long): Boolean {
        return bitboard and (bitboard - 1) == EMPTY
    }


    /**
     * Gets a bitboard and returns its string representation.
     */
    fun toPieceString(bitboard: Long, colour: Int): String {
        val buffer = StringBuilder()
        val rowSep = "+---+---+---+---+---+---+---+---+"
        buffer.append("  $rowSep\n")

        for (rank in Rank.RANK_8 downTo Rank.RANK_1) {
            buffer.append(Rank.toString(rank)).append(" |")
            for (file in 0 until 8) {
                val square = Square.getSquare(file, rank)
                if (colour == Color.WHITE) {
                    buffer.append(if (bitboard and getBitboard(square) != EMPTY) " W" else "  ")
                } else {
                    buffer.append(if (bitboard and getBitboard(square) != EMPTY) " B" else "  ")
                }
                buffer.append(" |")
            }
            buffer.append("  \n  $rowSep\n")

        }
        buffer.append("    a   b   c   d   e   f   g   h\n")

        return buffer.toString()
    }

    fun toStringAsBits(bitboard: Long, prefix: String = ""): String {
        val buffer = StringBuilder()
        var s = bitboard
        if (prefix != "") buffer.append(prefix.padEnd(32, ' '))
        for (i in 0 until Square.SIZE) {
            buffer.append((s and 1).toString())
            s = s shr 1
        }
        return buffer.toString()
    }
}

object Square {

    const val NONE = 64

    const val A1 = 0
    const val B1 = 1
    const val C1 = 2
    const val D1 = 3
    const val E1 = 4
    const val F1 = 5
    const val G1 = 6
    const val H1 = 7

    const val A2 = 8
    const val B2 = 9
    const val C2 = 10
    const val D2 = 11
    const val E2 = 12
    const val F2 = 13
    const val G2 = 14
    const val H2 = 15

    const val A3 = 16
    const val B3 = 17
    const val C3 = 18
    const val D3 = 19
    const val E3 = 20
    const val F3 = 21
    const val G3 = 22
    const val H3 = 23

    const val A4 = 24
    const val B4 = 25
    const val C4 = 26
    const val D4 = 27
    const val E4 = 28
    const val F4 = 29
    const val G4 = 30
    const val H4 = 31

    const val A5 = 32
    const val B5 = 33
    const val C5 = 34
    const val D5 = 35
    const val E5 = 36
    const val F5 = 37
    const val G5 = 38
    const val H5 = 39

    const val A6 = 40
    const val B6 = 41
    const val C6 = 42
    const val D6 = 43
    const val E6 = 44
    const val F6 = 45
    const val G6 = 46
    const val H6 = 47

    const val A7 = 48
    const val B7 = 49
    const val C7 = 50
    const val D7 = 51
    const val E7 = 52
    const val F7 = 53
    const val G7 = 54
    const val H7 = 55

    const val A8 = 56
    const val B8 = 57
    const val C8 = 58
    const val D8 = 59
    const val E8 = 60
    const val F8 = 61
    const val G8 = 62
    const val H8 = 63

    const val SIZE = 64

    val SQUARE_DISTANCE = Array(SIZE) { IntArray(SIZE) }
    val FILE_DISTANCE = Array(SIZE) { IntArray(SIZE) }
    val RANK_DISTANCE = Array(SIZE) { IntArray(SIZE) }

    private val CHARACTER = arrayOf(
        "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1",
        "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
        "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
        "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
        "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
        "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
        "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
        "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8", "-"
    )

    init {
        populateDistance()
    }

    fun getSquare(bitboard: Long): Int {
        return java.lang.Long.numberOfTrailingZeros(bitboard)
    }
    fun getSquare(file: Int, rank: Int): Int {
        return (rank shl Rank.RANK_SHIFT) + file
    }

    fun getSquare(square: String): Int {
        return CHARACTER.indexOf(square)
    }

    fun getRelativeSquare(color: Int, square: Int): Int {
        return square xor (A8 * color)
    }

    fun getSquareListFromBitboard(bitboard: Long): IntArray {
        if (bitboard == 0L) {
            return IntArray(0)
        }
        var squareCount = 0
        for (i in Long.SIZE_BITS downTo  0) {
            if (i and 1 == 1) {
                squareCount++
            }
        }
        val result = IntArray(squareCount)
        var index = 0
        for (i in 0 until SIZE) {
            if (bitboard and (1L shl i) != 0L) {
                result[index++] = i
                if (index == squareCount) {
                    break
                }
            }
        }
        return result
    }

    fun toString(square: Int): String {
        return CHARACTER[square]
    }

    private fun populateDistance() {
        for (square1 in 0 until SIZE) {
            val file1 = File.getFile(square1)
            val rank1 = Rank.getRank(square1)
            for (square2 in 0 until SIZE) {
                if (square1 == square2) {
                    continue
                }
                val file2 = File.getFile(square2)
                val rank2 = Rank.getRank(square2)
                SQUARE_DISTANCE[square1][square2] = calculateDistance(square1, square2)
                FILE_DISTANCE[square1][square2] = abs(file1 - file2)
                RANK_DISTANCE[square1][square2] = abs(rank1 - rank2)
            }
        }
    }

    fun calculateDistance(square1: Int, square2: Int): Int {
        return max(
            abs(File.getFile(square1) - File.getFile(square2)),
            abs(Rank.getRank(square1) - Rank.getRank(square2))
        )
    }

    fun isValid(square: Int): Boolean {
        return square >= A1 && square <= H8
    }

    fun flipHorizontal(square: Int): Int {
        return square and Square.A8 or File.FILE_H - (square and File.FILE_H)
    }
}

object BitboardMove {

    const val NORTH = 8
    const val SOUTH = -NORTH
    const val EAST = 1
    const val WEST = -EAST
    val NEIGHBOURS = LongArray(Square.SIZE)
    val PAWN_FORWARD = arrayOf(NORTH, SOUTH)
    val DOUBLE_PAWN_FORWARD = arrayOf(NORTH * 2, SOUTH * 2)

    init {
        populateNeighbours()
    }

    fun populateNeighbour (color:Int, fromBitboard: Long, toSquare: Int): Long {

        val rank = Rank.getRank(toSquare)
        return if (color == Color.WHITE) {
            (fromBitboard shl LEFT) or (fromBitboard shl +RIGHT) xor Bitboard.RANKS[rank]
        } else {
            (fromBitboard ushr LEFT) or (fromBitboard + RIGHT)
        }
    }

    private fun populateNeighbours() {
        for (square in Square.A1 until Square.SIZE) {
            val file = File.getFile(square)
            var boundBitboard = Bitboard.ALL

            when (file) {
                File.FILE_H -> {
                    boundBitboard = boundBitboard and Bitboard.NOT_FILE_A
                }
                File.FILE_A -> {
                    boundBitboard = boundBitboard and Bitboard.NOT_FILE_H
                }
            }
            var possibleNeighbours = Bitboard.EMPTY
            val westSquare = square + WEST
            if (Square.isValid(westSquare)) {
                possibleNeighbours = Bitboard.getBitboard(westSquare)
            }
            val eastSquare = square + EAST
            if (Square.isValid(eastSquare)) {
                possibleNeighbours = possibleNeighbours or Bitboard.getBitboard(eastSquare)
            }
            NEIGHBOURS[square] = boundBitboard and possibleNeighbours
        }
    }

    fun pawnMove(color: Int, bitboard: Long): Long {
        return if (color == Color.WHITE) {
            bitboard shl NORTH
        } else {
            bitboard ushr NORTH
        }
    }

    // Note: Pass a pawn move bitboard to this function
    fun pawnDoubleMove(color: Int, bitboard: Long): Long {
        return if (color == Color.WHITE) {
            (bitboard and Bitboard.RANK_3) shl NORTH
        } else {
            (bitboard and Bitboard.RANK_6) ushr NORTH
        }
    }

    fun pawnAttacks(color: Int, bitboard: Long): Long {
        return if (color == Color.WHITE) {
            (bitboard shl 7 and Bitboard.NOT_FILE_H) or (bitboard shl 9 and Bitboard.NOT_FILE_A)
        } else {
            (bitboard ushr 7 and Bitboard.NOT_FILE_A) or (bitboard ushr 9 and Bitboard.NOT_FILE_H)
        }
    }
}

object Color {

    const val INVALID = -1
    const val WHITE = 0
    const val BLACK = 1
    const val SIZE = 2

    private val CHARACTER = charArrayOf('w', 'b')
    private val STRING = arrayOf("White", "Black")
    fun invertColor(color: Int): Int {
        return color xor 1
    }

    fun getColor(character: Char): Int {
        return CHARACTER.indexOf(character)
    }

    fun toString(color: Int): Char {
        return CHARACTER[color]
    }

    fun fullColor(color: Int): String {
        return STRING[color]
    }
}

object MoveType {
    const val TYPE_NONE = 7
    const val TYPE_NORMAL = 0
    const val TYPE_NORMAL_ONE = 1
    const val TYPE_NORMAL_TWO = 2
    const val TYPE_PASSANT = 3
    const val TYPE_CAPTURE = 4
    const val INVALID = 6

    private val CHARACTER = charArrayOf('-', '-', '-', 'n', 'b', 'r', 'q', 'k')

    fun fetchMoveType(move: Int, colorToMove: Int): Int {

        // Get the move type
        val toSquare = Move.getToSquare(move)
        val fromSquare = Move.getFromSquare(move)
        val moveType: Int
        when (File.getFile(toSquare) - File.getFile(fromSquare)) {
            0 -> {
                when (Rank.getRank(toSquare) - Rank.getRank(fromSquare)) {
                    1 -> moveType = if (colorToMove == Color.WHITE) TYPE_NORMAL_ONE else INVALID
                    -1 -> moveType = if (colorToMove == Color.BLACK) TYPE_NORMAL_ONE else INVALID
                    2 -> moveType = if (colorToMove == Color.WHITE) TYPE_NORMAL_TWO else INVALID
                    -2 -> moveType = if (colorToMove == Color.BLACK) TYPE_NORMAL_TWO else INVALID
                    else -> moveType = INVALID
                }
            }
            1, -1 -> {
                when (Rank.getRank(toSquare) - Rank.getRank(fromSquare)) {
                    1 -> {
                        moveType = if (colorToMove == Color.WHITE) TYPE_CAPTURE else INVALID
                    }
                    -1 -> {
                        moveType = if (colorToMove == Color.BLACK) TYPE_CAPTURE else INVALID
                    }
                    else -> moveType = INVALID
                }
            }

            else -> moveType = INVALID
        }
        return moveType
    }
}

object Move {
    // Predefined moves
    const val NONE = 0
    const val NULL = 65535

    const val NONE_STRING = "none"
    const val NULL_STRING = "null"

    const val TO_SHIFT = 6
    const val MOVE_TYPE_SHIFT = 12

    private const val FROM_TO_MASK = 0xFFF

    const val SIZE = 16


    fun createMove(
        fromSquare: Int,
        toSquare: Int,
        moveType: Int = MoveType.TYPE_NORMAL
    ): Int {
        return (fromSquare or (toSquare shl TO_SHIFT) or (moveType shl MOVE_TYPE_SHIFT))
    }

    fun isValid(move: Int): Boolean {
        val rank = Rank.getRank(getToSquare(move))
        val file = File.getFile(getToSquare(move))
        return ((move != NONE && move != NULL) || !Rank.isValid(rank) || !File.isValid(file))
    }

    fun getFromSquare(move: Int): Int {
        return move and Square.H8
    }

    fun getToSquare(move: Int): Int {
        return (move ushr TO_SHIFT) and Square.H8
    }

    fun getMoveType(move: Int): Int {
        return move.ushr(MOVE_TYPE_SHIFT) and 0xf
    }

    fun toString(move: Int): String {
        return when (move) {
            NONE -> NONE_STRING
            NULL -> NULL_STRING
            else -> {
                val sb = StringBuilder()
                sb.append(Square.toString(getFromSquare(move)))
                sb.append(Square.toString(getToSquare(move)))
                sb.toString()
            }
        }
    }

    fun getMove(token: String): Int {
        val moveType = MoveType.TYPE_NORMAL
        val fromSquare = Square.getSquare(token.substring(0, 2))
        val toSquare = Square.getSquare(token.substring(2, 4))
        return createMove(fromSquare, toSquare, moveType)
    }

}

object GameState {

    const val PLAYING = 0
    const val HASWON = 1
    const val GAMEDRAWN = 2
    const val RESIGNED = 3
    const val ERROR = 4
    const val HASLOST = 5
    private val STATE = arrayOf("playing", "won", "drawn","resigned","error","lost")

    fun toString(state: Int) : String{
        return STATE[state]
    }
}

object Rank {
    private const val RANK_1_TOKEN = '1'
    const val RANK_SHIFT = 3

    const val INVALID = -1

    const val RANK_1 = 0
    const val RANK_2 = 1
    const val RANK_3 = 2
    const val RANK_4 = 3
    const val RANK_5 = 4
    const val RANK_6 = 5
    const val RANK_7 = 6
    const val RANK_8 = 7

    const val SIZE = 8

    fun getRank(square: Int): Int {
        return square shr RANK_SHIFT
    }

    fun getRank(token: Char): Int {
        return token - RANK_1_TOKEN
    }

    fun isValid(rank: Int): Boolean {
        return rank in RANK_1 until SIZE
    }

    fun toString(rank: Int): Char {
        return RANK_1_TOKEN + rank
    }

    fun getRelativeRank(color: Int, rank: Int): Int {
        return rank xor color * RANK_8
    }

    fun invertRank(rank: Int): Int {
        return rank xor RANK_8
    }
}

object Piece {

    const val NONE = 0
    const val PAWN = 1
    const val SIZE = 7

    private val CHARACTER = charArrayOf('-', 'P', 'N', 'B', 'R', 'Q', 'K', '+', 'p', 'n', 'b', 'r', 'q', 'k')

    fun getPiece(token: Char): Int {
        val indexOf = CHARACTER.indexOf(token)
        return if (indexOf <= NONE) {
            NONE
        } else indexOf.rem(SIZE)
    }

    fun getPieceColor(token: Char): Int {
        return if (CHARACTER.indexOf(token) < SIZE) Color.WHITE else Color.BLACK
    }

    fun toString(piece: Int): Char {
        return toString(Color.BLACK, piece)
    }

    fun toString(color: Int, piece: Int): Char {
        return CHARACTER[piece + SIZE * color]
    }

    fun isValidPiece(piece: Int): Boolean {
        return piece in PAWN until SIZE
    }

    fun isValid(piece: Int): Boolean {
        return piece in NONE until SIZE
    }
}

object File {

    const val FILE_A = 0
    const val FILE_H = 7

    const val SIZE = 8
    private val CHARACTER =
        charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H')

    fun getFile(token: Char): Int {
        return getFile(CHARACTER.indexOf(token))
    }

    fun getFile(square: Int): Int {
        return square and FILE_H
    }

    fun flipFile(file: Int): Int {
        return file xor FILE_H
    }

    fun isValid(file: Int): Boolean {
        return file in FILE_A until SIZE
    }

    fun toString(file: Int): Char {
        return CHARACTER[file]
    }
}
