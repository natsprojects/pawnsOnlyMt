package pawnChess

/**
 * https://chessprogramming.wikispaces.com/Move%20List
 */
class OrderedMoveList {

    val moves = IntArray(GameConstants.MAX_PLY_MOVES)
    val scores = IntArray(GameConstants.MAX_PLY_MOVES)
    var nextToMove = 0
    var nextToGenerate = 0
    var bestScored =0
    var moveLimit = 0
    var index = mutableListOf<Pair<Int,Int>>()

    //   var nextToGet = 0

    fun reset() {
        nextToMove = 0
        nextToGenerate = 0
       moveLimit = 0

    }

    fun getListScored(): List<Pair<Int,Int>> {


        var sortedListOfMoves = mutableListOf<Pair<Int,Int>>()
        for (index in range()) {
            sortedListOfMoves.add(Pair(moves[index], scores[index]))
        }
        restart()
        sortedListOfMoves.sortByDescending { it.second }
        return sortedListOfMoves
    }

    fun restart() {
        nextToMove = 0
    }
    fun getScore(): Int {

       return scores[nextToMove]
    }



    fun range(): IntRange {moveLimit

        return nextToMove  until nextToGenerate
    }

    fun size(color: Int = Color.WHITE): Int {
        return   range().count()
    }

    fun setPrune(pruneSize: Int)  {
        moveLimit = if ((nextToGenerate > pruneSize)  )  pruneSize else nextToGenerate


    }


    fun next(): Int {
        var bestScore = INVALID_SCORE
        var bestIndex = -1
        val nextIndex = nextToMove
        for (index in nextIndex until moveLimit) {
            val score = scores[index]
            if (score > bestScore) {
                bestScore = score
                bestIndex = index
            }
        }
        val move = moves[bestIndex]
        if (bestIndex != nextIndex  && bestIndex > -1) {
            moves[bestIndex] = moves[nextIndex]
            scores[bestIndex] = scores[nextIndex]

            moves[nextIndex] = move
        }
        nextToMove++
        return move
    }

    fun hasNext(): Boolean {
        return nextToMove != moveLimit
    }

    fun addMove(move: Int, score: Int) {
        scores[nextToGenerate] = score
        moves[nextToGenerate++] = move
        moveLimit++
    }

    fun contains(move: Int): Boolean {
        val left = nextToMove
        val right = nextToGenerate
        for (index in left..right) {
            if (moves[index] == move) {
                return true
            }
        }
        return false
    }

    override fun toString(): String {
        val left = nextToMove
        val right = nextToGenerate
        val buffer = StringBuilder()
        for (index in left until right) {
            buffer.append(Move.toString(moves[index]).padEnd(8,' ') + scores[index].toString() + "\n")
            buffer.append(' ')
        }
        return buffer.toString()
    }

    companion object {
        const val INVALID_SCORE = Int.MIN_VALUE

    }
}
