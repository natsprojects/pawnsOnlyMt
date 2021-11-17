package pawnChess
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class BoardTest {

   @Test
   fun `test that we can copy a board` () {
       val board = BoardFactory.getBoard()
       val board2 = BoardFactory.getBoard("8/p7/8/8/8/8/PPPPPPPP/8 w - -")
       board2.printBoard()
       board2.copy(board)
       board2.printBoard()
   }
    @Test
    fun `test that a move can not be made if no piece of same color at from square is available` () {
        // given a board at starter and a setup for white to move
        val board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -")
        println(Bitboard.toStringAsBits(RANK_TWO_MASK))
        //when we make the move
        val move = Move.createMove(Square.B3, Square.B4)

        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        board.printBoard()
        // then we get the correct error message
        assertEquals("no white pawn at b3",board.errorMessage)

        // and its still whites turn
        assertEquals(board.colorToMove, Color.WHITE)

        // and pieces are still at starting places
        assertEquals( board.pieceBitboard[Color.WHITE][Piece.PAWN] and  Bitboard.B2 ,Bitboard.B2)
    }
    @Test
    fun `test a black pawn and a white pawn can move one square from starter`() {
        // given a board at starter and a setup for white to move
        val board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -")

        var move = Move.createMove(Square.B2, Square.B3)

        // and its still whites turn
        assertEquals(board.colorToMove, Color.WHITE)

        // and pieces are still at starting places
        assertEquals( board.pieceBitboard[Color.WHITE][Piece.PAWN] and  Bitboard.B2 ,Bitboard.B2)

        // then we make the move
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }

        board.printBoard()

        //and check its legal
        assertEquals(board.colorToMove, Color.BLACK)
        assertEquals( board.pieceBitboard[Color.WHITE][Piece.PAWN] and  Bitboard.B3 ,Bitboard.B3)

        // when its blacks turn to create a move
        move = Move.createMove(Square.A7, Square.A6)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        board.printBoard()

        // then the color has flipped, position is setup
        assertEquals(board.colorToMove, Color.WHITE)
        assertEquals(board.nextColorToMove, Color.BLACK)
        assertEquals( Bitboard.A6,board.pieceBitboard[Color.BLACK][Piece.PAWN] and Bitboard.A6 )
        assertNotEquals(Bitboard.A7,board.pieceBitboard[Color.BLACK][Piece.PAWN] and Bitboard.A7)

    }


    @Test
    fun `test a white pawn then a black pawn can move two squares from starter `() {
        // Given a board and two pawns on starer squares

        val board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -")
        var move = Move.createMove(Square.B2, Square.B4)


        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }

        //then we should have the white pawn move two squares

        assertEquals(board.colorToMove, Color.BLACK)
        assertEquals(board.moveNumber, 1)
        assertEquals(Bitboard.B4,board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.B4)
        assertNotEquals(Bitboard.B2, board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.B2)

        // given a black pawn on starter black can move  2 squares forward
        move = Move.createMove(Square.B7, Square.B5)

        // when we make the move
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        // then the black pawn should have moved two squares forward

        board.printBoard()
        assertEquals(board.colorToMove, Color.WHITE)
        assertEquals(board.moveNumber, 2)
        assertEquals(Bitboard.B5, board.pieceBitboard[Color.BLACK][Piece.PAWN] and Bitboard.B5)

    }

    @Test
    fun `test a pawn can not move backwards  `() {
        // Given a board and a white pawn that is blocked by a black pawn

        val board = BoardFactory.getBoard("8/pppp4/8/8/3Ppppp/1p6/1P6/8 w - -")
        var move = Move.createMove(Square.B2, Square.B1)

        // when we make the white pawn move

        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }

        //then we should have the white pawn not able to move

        assertEquals(Color.WHITE,board.colorToMove)
        assertEquals(0,board.moveNumber)
        assertEquals(Bitboard.B2, board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.B2)
        assertNotEquals(Bitboard.B3, board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.B1)
        assertEquals("Invalid Input",board.errorMessage)

    }

    @Test
    fun `test a pawn can not move if it is blocked  `() {
        // Given a board and a white pawn that is blocked by a black pawn

        val board = BoardFactory.getBoard("8/pppp4/8/8/3Ppppp/1p6/1P6/8 w - -")
        var move = Move.createMove(Square.B2, Square.B3)

        // when we make the white pawn move

        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }

        //then we should have the white pawn not able to move

        assertEquals(Color.WHITE,board.colorToMove)
        assertEquals(0,board.moveNumber)
        assertEquals(Bitboard.B2, board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.B2)
        assertNotEquals(Bitboard.B3, board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.B3)
        assertEquals("Invalid Input",board.errorMessage)
        // then Given a double pawn move from same position
        move = Move.createMove(Square.B2, Square.B4)

        // when we make the white pawn move two squares

        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }

        //then the white pawn is not able to move
        println(board.errorMessage)
        board.printBoard()
        assertEquals( Color.WHITE,board.colorToMove)
        assertEquals(0,board.moveNumber)
        assertEquals(Bitboard.B2,board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.B2)
        assertNotEquals(Bitboard.B4,board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.B4)
        //       assertEquals("Invalid", board.errorMessage  )
    }

    @Test
    fun `test that white can capture a pawn`() {
        // given a board with a white capture setup
        val board = BoardFactory.getBoard("8/pp6/8/3p4/4P3/8/8/8 w - -")

        val move = Move.createMove(Square.E4, Square.D5)

        // when we make the move

        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }

        board.printBoard()
        // then the black piece should be captured
        println(board.errorMessage)
        assertEquals(board.colorToMove, Color.BLACK)
        assertNotEquals(board.pieceBitboard[Color.WHITE][Piece.PAWN], Bitboard.E4)
        assertEquals(board.pieceBitboard[Color.WHITE][Piece.PAWN], Bitboard.D5)

        //       assertEquals(0, board.pieceBitboard[Color.BLACK][Piece.PAWN])
        assertNotEquals(board.pieceBitboard[Color.BLACK][Piece.PAWN], Bitboard.E4)
    }

    @Test
    fun `test that only legal white pawn pushes and captures can be made` () {
        // given a starter position
        val board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -")

        // when we make an illegal white pawn push of 3 squares forward

        var move = Move.createMove(Square.B2, Square.B5)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        board.printBoard()

        //then we should have the white pawn not able to move

        assertEquals(Color.WHITE,board.colorToMove)
        assertEquals(0,board.moveNumber)
        assertEquals(Bitboard.B2, board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.B2)
        assertNotEquals(Bitboard.B5, board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.B5)
        assertEquals("Invalid Input",board.errorMessage)
    }

    @Test
    fun `test that only legal black pawn pushes and captures can be made` () {
        // given a starter position
        val board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 b - -")

        // when we make an illegal black pawn push of 3 squares forward

        val move = Move.createMove(Square.B7, Square.B4)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        board.printBoard()

        //then we should have the white pawn not able to move

        assertEquals(Color.BLACK,board.colorToMove)
 //       assertEquals(0,board.moveNumber)
        assertEquals(Bitboard.B7, board.pieceBitboard[Color.BLACK][Piece.PAWN] and Bitboard.B7)
        assertNotEquals(Bitboard.B4, board.pieceBitboard[Color.BLACK][Piece.PAWN] and Bitboard.B4)
        assertEquals("Invalid Input",board.errorMessage)
    }

    @Test
    fun `test that a white pawn cannot move sideways if no capture` () {
        // given a starter position
        val board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 w - -")

        // when we make an illegal black sideways

        var move = Move.createMove(Square.B2, Square.C3)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }


        //then we should have original starting position

        assertEquals(Color.WHITE,board.colorToMove)
        //       assertEquals(0,board.moveNumber)
        assertEquals(Bitboard.B2, board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.B2)
        assertNotEquals(Bitboard.C3, board.pieceBitboard[Color.WHITE][Piece.PAWN] and Bitboard.C3)
        assertEquals("Invalid Input",board.errorMessage)

        move = Move.createMove(Square.A2, Square.A4)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        assertEquals(1,board.moveNumber)
        assertEquals(Color.BLACK,board.colorToMove)
        move = Move.createMove(Square.A7, Square.A6)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        assertEquals(2,board.moveNumber)
        assertEquals(Color.WHITE,board.colorToMove)
        move = Move.createMove(Square.A4, Square.A5)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        assertEquals(3,board.moveNumber)
        move = Move.createMove(Square.H7, Square.H5)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        assertEquals(4,board.moveNumber)

        move = Move.createMove(Square.A5, Square.B6)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        assertEquals(4,board.moveNumber)
    }

    @Test
    fun `test that a black pawn cannot move sideways if no capture` () {
        // given a starter position
        val board = BoardFactory.getBoard("8/pppppppp/8/8/8/8/PPPPPPPP/8 b - -")

        // when we make an illegal black sideways

        val move = Move.createMove(Square.B7, Square.A6)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }


        //then we should have original starting position

        assertEquals(Color.BLACK,board.colorToMove)
        //       assertEquals(0,board.moveNumber)
        assertEquals(Bitboard.B7, board.pieceBitboard[Color.BLACK][Piece.PAWN] and Bitboard.B7)
        assertNotEquals(Bitboard.A6, board.pieceBitboard[Color.BLACK][Piece.PAWN] and Bitboard.A6)
        assertEquals("Invalid Input",board.errorMessage)
    }
    @Test
    fun `test that a black pawn cannot move two squares if not from rank 2` () {
        // given a  position
        val board = BoardFactory.getBoard("8/1ppppppp/p7/8/8/1P7/1PPPPPPP/8 b - -")

        // when we make

        val move = Move.createMove(Square.A6, Square.A4)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        board.printBoard()

        //then we should have an illegal move ar original  position

        assertEquals(Color.BLACK,board.colorToMove)
        //       assertEquals(0,board.moveNumber)
        assertEquals(Bitboard.A6, board.pieceBitboard[Color.BLACK][Piece.PAWN] and Bitboard.A6)
        assertNotEquals(Bitboard.A4, board.pieceBitboard[Color.BLACK][Piece.PAWN] and Bitboard.A4)
        assertEquals("Invalid Input",board.errorMessage)
    }

    @Test
    fun `test a Passant Move sets up the epSquare on G3`() {

        // Given a board with a potential passant move after G2G4

        val board = BoardFactory.getBoard("8/8/8/8/7p/8/6P1/8 w - -")
        var move = Move.createMove(Square.G2, Square.G4)

        // when we make the move
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        board.printBoard()

        //then the board should recognise this is a passant move
        assertEquals(Square.G3,board.epSquare)

    }

    @Test
    fun `test that a white pawn can be captured enpassent`() {

        // given a board with a passent square on G3 with black to move

        val board = BoardFactory.getBoard("8/8/8/8/6Pp/8/8/8 b - g3")
        val move = Move.createMove(Square.H4, Square.G3)

        // When we take the pawn enpassent

        assertEquals(board.colorToMove, Color.BLACK)
        if (MoveGen.isLegalMove(board,move)) {

            board.doMove(move)
        }
        board.printBoard()
        assertEquals(board.pieceBitboard[Color.BLACK][Piece.PAWN], Bitboard.G3)
        assertEquals(0,board.pieceBitboard[Color.WHITE][Piece.PAWN])

    }

    @Test
    fun `test that a pawn cannot be captured enpassent if the empassent square is not set`() {

        // given a board without a passent square on G3 with black to move

        val board = BoardFactory.getBoard("8/8/8/8/6Pp/8/8/8 b - -")
        val move = Move.createMove(Square.H4, Square.G3, MoveType.TYPE_PASSANT)
        board.printBoard()

        // When we try to take the pawn enpassent

        assertEquals(board.colorToMove, Color.BLACK)
        if (MoveGen.isLegalMove(board,move)) {
            board.doMove(move)
        }
        println(board.errorMessage)
        board.printBoard()

        // then the white pawn should not be captured

        assertEquals(board.colorToMove, Color.BLACK)
        assertNotEquals(board.pieceBitboard[Color.BLACK][Piece.PAWN], Bitboard.G3)
        assertNotEquals(0,board.pieceBitboard[Color.WHITE][Piece.PAWN])

  //      assertEquals("Invalid Input",board.errorMessage)
    }

    @Test
    fun `test that we can generate all the legal moves with just one white pawn` () {
        // given a board with one white pawn and a black
        val fen ="8/7p/8/7p/7p/8/P7/8 w - -"
        var board = BoardFactory.getBoard(fen)
       board.printBoard()
        // when we generate all the moves for white
        val legalMoves = MoveGen.generateLegalMoves(board, Color.WHITE)
        assertEquals(2,legalMoves.count())
        for (move in legalMoves) {
            board = BoardFactory.getBoard(fen)
            if (MoveGen.isLegalMove(board,move)) {
                board.doMove(move)
                board.printBoard()
            } else assertTrue { false }
        }
    }

    @Test
    fun `test that we can generate all the legal moves with just one black pawn` () {

        val fen ="8/7p/8/2P5/1p6/7P/P7/8 b - -"
        var board = BoardFactory.getBoard(fen)
        board.printBoard()
        // when we generate all the moves for white
        val legalMoves = MoveGen.generateLegalMoves(board,Color.BLACK)
        assertEquals(3,legalMoves.count())
        for (move in legalMoves) {
            board = BoardFactory.getBoard(fen)
            if (MoveGen.isLegalMove(board,move)) {
                board.doMove(move)
                board.printBoard()
            } else assertTrue { false }
        }
    }
}




