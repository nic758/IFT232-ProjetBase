package chess;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import chess.ui.Gameview;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ChessGame {


    //Planche de jeu (incluant les pi�ces)
    private ChessBoard board;

    //Charge une planche de jeu à partir d'un fichier.
    public void loadBoard(String  path) {
       File file = new File(path);
      loadBoard(file, 0,0);
    }

    //Charge une planche de jeu à partir d'un fichier.
    public void loadBoard(File file, int x, int y) {
        try {
            board = ChessBoard.readFromFile(file, x, y);

            // Attention! Le board peut masquer les autres contrôles s'il n'est pas
            // placé complètement derrière eux.
            board.getPane().toBack();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, "Error reading file", ButtonType.OK);
            alert.showAndWait();
            return;
        }
    }

    //Sauvegarde la planche de jeu actuelle dans un fichier.
    public void saveBoard(File file) {

        try {
            board.saveToFile(file);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, "Error writing file", ButtonType.OK);
            alert.showAndWait();
            return;
        }
    }

    //Démarre l'enregistrement des mouvements du jeu dans un fichier de script.
    public void saveScript(File file) {

        try {
            throw new Exception("Pas implanté!");
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, "Error writing file", ButtonType.OK);
            alert.showAndWait();
            return;
        }

    }

    //Charge un fichier de script
    public void loadScript(File file) {

        try {
            throw new Exception("Pas implanté!");
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR, "Error reading file", ButtonType.OK);
            alert.showAndWait();
            return;
        }

    }

    public ChessBoard getChessBoard() {
        return board;
    }

    public void setChessBoard(ChessBoard chessBoard) {
        board = chessBoard;
    }

    public void movePiece(String s) {
        Point pts1 = ChessUtils.convertAlgebraicPosition(s.substring(0,2));
        Point pts2 = ChessUtils.convertAlgebraicPosition(s.substring(3,5));

        board.move(pts1, pts2);
    }

    public boolean compareBoard(ChessBoard result) {
        return  result.equals(result);
    }
}
