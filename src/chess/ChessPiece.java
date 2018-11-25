package chess;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;

import chess.ui.BoardView;
import chess.ui.PieceView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static chess.ChessUtils.makeAlgebraicPosition;
import static chess.ChessUtils.makePieceName;


public class ChessPiece {


    // Position de la pièce sur l'échiquier
    private int gridPosX;
    private int gridPosY;

    private int type;
    private int color;
    private PieceView pieceView;

    // Pour créer des pièces à mettre sur les cases vides
    public ChessPiece(int x, int y, ChessBoard b) {
        this.type = ChessUtils.TYPE_NONE;
        this.color = ChessUtils.COLORLESS;
        gridPosX = x;
        gridPosY = y;
        pieceView = new PieceView(x, y, b);
        new BoardView(x, y, b);
    }

    // Création d'une pièce normale. La position algébrique en notation d'échecs
    // lui donne sa position sur la grille.
    public ChessPiece(String name, String pos, ChessBoard b) {
        color = ChessUtils.getColor(name);
        type = ChessUtils.getType(name);
        pieceView = new PieceView(b, this);

        setAlgebraicPos(pos);
    }

    //Change la position avec la notation algébrique
    public void setAlgebraicPos(String pos) {

        Point pos2d = ChessUtils.convertAlgebraicPosition(pos);

        gridPosX = pos2d.x;
        gridPosY = pos2d.y;
    }

    //Pour savoir si c'est une pièce vide (pour les cases vides de l'échiquier).
    public boolean isNone() {

        return type == ChessUtils.TYPE_NONE;
    }

    //Accesseurs divers
    public Pane getUI() {
        return pieceView.getPane();
    }

    public int getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    public int getGridX() {
        return gridPosX;
    }

    public int getGridY() {
        return gridPosY;
    }

    public Point getGridPos() {
        return new Point(gridPosX, gridPosY);
    }

    public void setGridPos(Point pos) {
        gridPosX = pos.x;
        gridPosY = pos.y;
    }

    public void saveToStream(FileWriter fileWriter) throws Exception {
        try {
            String test = makePieceName(color, type) + "-" + makeAlgebraicPosition(gridPosX, gridPosY);
            fileWriter.write(makeAlgebraicPosition(gridPosX, gridPosY) + "-" + makePieceName(color, type) + "\n");
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public boolean equals(Object obj) {
        ChessPiece chessPiece = (ChessPiece) obj;

        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ChessBoard)) return false;

        if (gridPosX == chessPiece.getGridX() &&
                gridPosY == chessPiece.getGridY()
                && type == chessPiece.getType() && color == chessPiece.getColor()) {
            return true;
        }
        return false;
    }

    public boolean verifyMove(Point startPos, Point endPos) {
        //TODO:to refactor.
        int deltaX = endPos.x - startPos.x;
        int deltaY = endPos.y - startPos.y;

        switch (type) {
            case ChessUtils.TYPE_PAWN:
                if (deltaX == 0) {
                    if (color == ChessUtils.WHITE) {
                        return deltaY == -1;
                    } else if (color == ChessUtils.BLACK) {
                        return deltaY == 1;
                    } else {
                        return false;
                    }
                }
                return false;

            case ChessUtils.TYPE_KNIGHT:
                if ((Math.abs(deltaX) == 1 && Math.abs(deltaY) == 2) || (Math.abs(deltaX) == 2 && Math.abs(deltaY) == 1)) {
                    return true;
                }
                return false;

            case ChessUtils.TYPE_BISHOP:
                if (Math.abs(deltaX) == Math.abs(deltaY)) {
                    return true;
                }
                return false;

            case ChessUtils.TYPE_ROOK:
                if (deltaX == 0 || deltaY == 0) {
                    return true;
                }
                return false;

            case ChessUtils.TYPE_QUEEN:
                if ((Math.abs(deltaX) == 1 && Math.abs(deltaY) == 2) || (Math.abs(deltaX) == 2 && Math.abs(deltaY) == 1)) {
                    return false;
                }
                return true;

            case ChessUtils.TYPE_KING:
                if ((Math.abs(deltaX) - Math.abs(deltaY)) == 0 || (Math.abs(deltaX) + Math.abs(deltaY)) == 1) {
                    return true;
                }
                return false;

            default:
                return false;
        }
    }

}
