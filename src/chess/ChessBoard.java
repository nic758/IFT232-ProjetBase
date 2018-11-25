package chess;

import chess.ui.BoardView;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.stream.Stream;

import static chess.ChessUtils.TYPE_NONE;

//Représente la planche de jeu avec les pièces.


public class ChessBoard {

    private BoardView boardView;

    // Grille de jeu 8x8 cases. Contient des références aux piéces présentes sur
    // la grille.
    // Lorsqu'une case est vide, elle contient une pièce spéciale
    // (type=ChessPiece.NONE, color=ChessPiece.COLORLESS).
    private ChessPiece[][] grid;

    public ChessBoard(int x, int y) {
        boardView = new BoardView(x, y, this);

        // Initialise la grille avec des pièces vides.
        grid = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                grid[i][j] = new ChessPiece(i, j, this);
            }
        }
    }

    // Place une pièce sur le planche de jeu.
    public void putPiece(ChessPiece piece) {

        Point2D pos = boardView.gridToPane(piece.getGridX(), piece.getGridY());
        piece.getUI().relocate(pos.getX(), pos.getY());
        getPane().getChildren().add(piece.getUI());
        grid[piece.getGridX()][piece.getGridY()] = piece;
    }


    public Pane getPane() {
        return boardView.getUI();
    }

    //Les cases vides contiennent une pièce spéciale
    public boolean isEmpty(Point pos) {
        return (grid[pos.x][pos.y].getType() == TYPE_NONE);
    }

    //Vérifie si une coordonnée dans la grille est valide
    public boolean isValid(Point pos) {
        return (pos.x >= 0 && pos.x <= 7 && pos.y >= 0 && pos.y <= 7);
    }

    //Vérifie si les pièces à deux positions dans la grille sont de la même couleur.
    public boolean isSameColor(Point pos1, Point pos2) {
        return grid[pos1.x][pos1.y].getColor() == grid[pos2.x][pos2.y].getColor();
    }

    //Effectue un mouvement à partir de la notation algébrique des cases ("e2-b5" par exemple)
    public void algebraicMove(String move) {
        if (move.length() != 5) {
            throw new IllegalArgumentException("Badly formed move");
        }
        String start = move.substring(0, 2);
        String end = move.substring(3, 5);
        move(ChessUtils.convertAlgebraicPosition(start), ChessUtils.convertAlgebraicPosition(end));
    }

    //Effectue un mouvement sur l'échiqier. Quelques règles de base sont implantées ici.
    public boolean move(Point2D pos, Point2D newPos) {
        Point gridPos = boardView.paneToGrid(pos.getX(), pos.getY());
        Point newGridPos = boardView.paneToGrid(newPos.getX(), newPos.getY());

        return move(gridPos, newGridPos);
    }

    public boolean move(Point gridPos, Point newGridPos) {
        
        ChessPiece toMove = getPiece(gridPos);

        if(toMove==null)
        {
            return false;
        }
        //Vérifie si les coordonnées sont valides
        if (!toMove.verifyMove(gridPos, newGridPos) || !isValid(newGridPos))
            return false;

            //Si la case destination est vide, on peut faire le mouvement
        else if (isEmpty(newGridPos)) {

            return assignSquare(newGridPos, toMove);
        }

        //Si elle est occuppé par une pièce de couleur différente, alors c'est une capture
        else if (!isSameColor(gridPos, newGridPos)) {

            removePiece(newGridPos);

            return assignSquare(newGridPos, toMove);
        }

        return false;
    }

    private ChessPiece getPiece(Point gridPos) {
        for (int i = 0 ; i < grid.length; i++)
        {
            for (int j = 0; j<grid[i].length; j++)
            {
                if(grid[i][j].getGridX() == gridPos.x && grid[i][j].getGridY() == gridPos.y)
                {
                    return grid[i][j];
                }
            }
        }

        return null;
    }

    public boolean assignSquare(Point pts, ChessPiece chessPiece) {
        grid[pts.x][pts.y] = grid[chessPiece.getGridX()][chessPiece.getGridY()];
        grid[chessPiece.getGridX()][chessPiece.getGridY()] = new ChessPiece(chessPiece.getGridX(), chessPiece.getGridY(), this);

        return true;
    }

    public void clearSquare(Point pts)
    {
        grid[pts.x][pts.y] = new ChessPiece(pts.x, pts.y, this);
    }

    public void removePiece(Point pts)
    {
        getPane().getChildren().remove(grid[pts.x][pts.y].getUI());

        clearSquare(pts);

    }



    //Convertit des coordonnées en pixels sur la fenêtre d'interface en coordonnées dans la grille de l'échiquier
    //Utilisé pour détecter qu'on a touché une case spécifique de la grille.
    public Point paneToGrid(double xPos, double yPos) {
        return boardView.paneToGrid(xPos, yPos);
    }

    public Point2D gridToPane(int x, int y) {
        return boardView.gridToPane(x, y);
    }

    //Fonctions de lecture et de sauvegarde d'échiquier dans des fichiers. À implanter.

    public static ChessBoard readFromFile(String fileName) throws Exception {
        return readFromFile(new File(fileName), 0, 0);
    }

    public static ChessBoard readFromFile(File file, int x, int y) throws Exception {
        Scanner sc = new Scanner(file);
        ChessBoard board = new ChessBoard(x, y);

        while (sc.hasNextLine()) {
            board.putPiece(readFromStream(sc.nextLine(), board));
        }
        return board;
    }

    public static ChessPiece readFromStream(String stream, ChessBoard board) {
        return new ChessPiece(stream.substring(3, 5), stream.substring(0, 2), board);
    }


    public void saveToFile(File file) throws Exception {
        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (grid[i][j].getType() != TYPE_NONE) {
                    grid[i][j].saveToStream(fileWriter);
                }
            }
        }

        fileWriter.flush();
        fileWriter.close();
    }

    @Override
    public boolean equals(Object obj) {
        ChessBoard board = (ChessBoard) obj;

        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof ChessBoard)) return false;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; i++) {
                if (!grid[i][j].equals(board.grid[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

}
