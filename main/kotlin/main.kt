package pawnChess

fun main() {
    val game = PawnChess()
   game.doGameLoop()
}
class PawnChess {

    var board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -")
    var position = Position(board)
    fun doGameLoop() {
        println("Pawns-Only Chess")
        println("First Player's name: \n ")
        val playerWhite = Player(readLine()!!, position, Color.WHITE, PlayerType.COMPUTER)
        println("Second Player's name: \n ")
        val playerBlack = Player(readLine()!!, position, Color.BLACK, PlayerType.COMPUTER)
        position.board.printBoard()
        while (true) {
            if (!playerWhite.getMove()) {
                break
            }
            if (!playerBlack.getMove()) {
                break
            }
        }
        println(position.leaveMessage)
    }
}


