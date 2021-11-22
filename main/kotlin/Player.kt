package pawnChess

class Player(
    var name: String? = "Guest",
    var position: Position,
    var ourColor: Int,
    val playerType: Int = PlayerType.COMPUTER

) {
    var resigns: Boolean = false
    var state: Int = GameState.PLAYING
    var leaveMessage = ""
    var valid = false
    fun humanMove() {
        val r = Regex("[a-h][1-8][a-h][1-8]")
        var moveStr: String

        do {
            println("${name}'s turn:")
            when (val playersMove = readLine()!!) {
                "exit" -> {
                    resigns = true
                    position.leaveMessage = "Bye!"
                    break
                }
                else -> {
                    moveStr = playersMove
                }
            }
            if (moveStr.matches(r)) {
                val move = Move.getMove(moveStr)
                if (MoveGen.isLegalMove(position.board, move)) {
                    position.board.doMove(move)
                    valid = true
                } else {
                    valid = false
                    println(position.board.getMessage())
                }
            } else valid = false
        } while (!valid)
    }

    fun computerMove() {

        val search = Search(position.board,4)
        val slist = search.scoredMoves()
        if (MoveGen.isLegalMove(position.board, slist[0].first)) {
            position.board.doMove(slist[0].first)
 //           slist.forEach { println("move: ${Move.toString(it.first)} ${it.second}") }
        } else assert(false)

    }

    fun getMove(): Boolean {
        when (playerType) {
            PlayerType.HUMAN -> humanMove()
            PlayerType.COMPUTER -> computerMove()
        }
        position.board.printBoard()
        if (position.gameStateEnd(GameState.GAMEDRAWN, position.board.colorToMove)  ||
           /* position.gameStateEnd(GameState.HASLOST, position.board.colorToMove) ||*/
            position.gameStateEnd(GameState.HASWON, position.board.colorToMove)

        ) {
            position.board.printBoard()
            return false
        }
        return !resigns
    }
}

