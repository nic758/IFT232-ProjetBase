package chess.tests;

import chess.ChessBoard;
import chess.ChessGame;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MoveTest {
    @Test
    public void testBasicCollision() throws Exception {
        ChessGame game = new ChessGame();
        game.loadBoard("boards/normalStart");
        ChessBoard result = ChessBoard.readFromFile("boards/normalStart");
        //Move tower over a pawn of the same color
        game.movePiece("a1-a2");
        assertTrue(game.compareBoard(result));
    }

    @Test
    public void testKingMovement() throws Exception {
        ChessGame game = new ChessGame();
        game.loadBoard("boards/saves/roi");
        ChessBoard result = ChessBoard.readFromFile("boards/saves/roi");
        //Move tower over a pawn of the same color
        game.movePiece("d5-c7");
        assertTrue(game.compareBoard(result));
    }

    @Test
    public void testQueenMovement() throws Exception {
        ChessGame game = new ChessGame();
        game.loadBoard("boards/saves/reine");
        ChessBoard result = ChessBoard.readFromFile("boards/saves/reine");
        //Move tower over a pawn of the same color
        game.movePiece("d5-c7");
        assertTrue(game.compareBoard(result));
    }

    @Test
    public void testBishopMovement() throws Exception {
        ChessGame game = new ChessGame();
        game.loadBoard("boards/saves/fou");
        ChessBoard result = ChessBoard.readFromFile("boards/saves/fou");
        //Move tower over a pawn of the same color
        game.movePiece("d5-d6");
        assertTrue(game.compareBoard(result));
    }

    @Test
    public void testKnightMovement() throws Exception {
        ChessGame game = new ChessGame();
        game.loadBoard("boards/saves/cheval");
        ChessBoard result = ChessBoard.readFromFile("boards/saves/cheval");
        //Move tower over a pawn of the same color
        game.movePiece("d5-d7");
        assertTrue(game.compareBoard(result));
    }

    public void testRooktMovement() throws Exception {
        ChessGame game = new ChessGame();
        game.loadBoard("boards/saves/tour");
        ChessBoard result = ChessBoard.readFromFile("boards/saves/tour");
        //Move tower over a pawn of the same color
        game.movePiece("d5-c7");
        assertTrue(game.compareBoard(result));
    }

    public void testPawmMovement() throws Exception {
        ChessGame game = new ChessGame();
        game.loadBoard("boards/saves/pion");
        ChessBoard result = ChessBoard.readFromFile("boards/saves/pion");
        //Move tower over a pawn of the same color
        game.movePiece("d5-c7");
        assertTrue(game.compareBoard(result));
    }
}
