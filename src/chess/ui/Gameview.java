package chess.ui;

import chess.ChessBoard;
import chess.ChessGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Gameview extends Application {

    private ChessGame chessGame;

    //Dialogue utilis� pour choisir des noms de fichiers
    private FileChooser fileDialog;
    //Panneau principal dans lequel se trouvent les éléments de jeu
    private Pane gamePane;

    //Taille de la fenêtre
    private int gameSizeX = 1200;
    private int gameSizeY = 1000;

    //Position de l'échiquier dans la fenêtre
    private int boardPosX = 200;
    private int boardPosY = 100;

    //Objet racine de l'interface graphique
    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    //Méthode de démarrage standard pour une application JavaFX.
    //L'initialisation de l'interface graphique se fait ici.
    @Override
    public void start(Stage stage) {

        //Création des boutons et des actions correspondantes.
        //Les actions sont exprimées sous forme de lambda-expressions (Java 8)

        fileDialog = new FileChooser();
        gamePane = new Pane();
        chessGame = new ChessGame();
        //Charge la planche de jeu par d�faut
        resetGame();




        //Bouton de redémarrage (recharge la planche de jeu par défaut)
        Button resetButton = new Button("Reset");
        resetButton.setLayoutX(250);
        resetButton.setLayoutY(50);
        gamePane.getChildren().add(resetButton);

        resetButton.setOnAction(event -> resetGame());


        //Bouton utilisé pour enregistrer les mouvements dnas un script
        Button recordButton = new Button("Record moves");
        recordButton.setLayoutX(325);
        recordButton.setLayoutY(50);
        gamePane.getChildren().add(recordButton);
        recordButton.setOnAction(event -> {

            File file = getSaveFile("Record moves...", "scripts/saves", stage);
            chessGame.saveScript(file);
        });

        //Bouton utilisé pour sauvegarder la planche de jeu
        Button saveButton = new Button("Save board");
        saveButton.setLayoutX(425);
        saveButton.setLayoutY(50);
        gamePane.getChildren().add(saveButton);

        saveButton.setOnAction(event -> {

            File file = getSaveFile("Save Board...", "boards/saves", stage);
            chessGame.saveBoard(file);
        });

        //Boîte de sélection utilisée pour activer l'intelligence artificielle
        CheckBox aiBox = new CheckBox("AI player");
        aiBox.setLayoutX(525);
        aiBox.setLayoutY(50);

        gamePane.getChildren().add(aiBox);


        //Bouton utilisé pour charger une planche de jeu
        Button loadButton = new Button("Load board");
        loadButton.setLayoutX(625);
        loadButton.setLayoutY(50);
        gamePane.getChildren().add(loadButton);

        loadButton.setOnAction(event -> {

            File file = getOpenFile("Open Board...", "boards", stage);

            resetGame(file);
        });


        //Bouton utilisé pour charger et exécuter une ancienne partie
        Button playButton = new Button("Play moves");
        playButton.setLayoutX(725);
        playButton.setLayoutY(50);
        gamePane.getChildren().add(playButton);

        playButton.setOnAction(event -> {
            File file = getOpenFile("Open Script...", "scripts", stage);
            chessGame.loadScript(file);
        });

        //Bouton undo, utilisé pour défaire le dernier mouvement
        Button undoButton = new Button("Undo");
        undoButton.setLayoutX(825);
        undoButton.setLayoutY(50);
        gamePane.getChildren().add(undoButton);

        //Bouton redo, utilisé pour refaire un mouvement défait
        Button redoButton = new Button("Redo");
        redoButton.setLayoutX(900);
        redoButton.setLayoutY(50);
        gamePane.getChildren().add(redoButton);


        //Boîte de sélection utilisée pour activer l'édition d'échiquier (déposer manuellement des pièces)
        CheckBox editBox = new CheckBox("Edit board");
        editBox.setLayoutX(200);
        editBox.setLayoutY(950);

        gamePane.getChildren().add(editBox);

        //Étiquette pour la zone de capture des noirs
        Label blackCapture = new Label("Black captures");
        blackCapture.setLayoutX(50);
        blackCapture.setLayoutY(200);
        gamePane.getChildren().add(blackCapture);

        //Étiquette pour la zone de capture des blancs
        Label whiteCapture = new Label("White captures");
        whiteCapture.setLayoutX(1050);
        whiteCapture.setLayoutY(200);
        gamePane.getChildren().add(whiteCapture);

        //Préparation de la fenêtre principale
        scene = new Scene(gamePane, gameSizeX, gameSizeY);

        stage.setTitle("Super Mega Chess 3000");

        stage.setScene(scene);

        stage.show();


    }


    //Retire la planche de jeu de la fenêtre
    public void clearGame(ChessBoard board) {
        if (board != null) {
            gamePane.getChildren().remove(board.getPane());
        }
    }

    //Redémarre le jeu avec une planche de jeu chargée d'un fichier
    public void resetGame() {
        if(chessGame.getChessBoard() != null)
        gamePane.getChildren().add(chessGame.getChessBoard().getPane());

        resetGame(new File("boards/normalStart"));
    }

    //Redémarre le jeu avec la planche de jeu par défaut.
    public void resetGame(File file) {

        clearGame(chessGame.getChessBoard());
        try {
            chessGame.setChessBoard(ChessBoard.readFromFile(file, boardPosX, boardPosY));
            gamePane.getChildren().add(chessGame.getChessBoard().getPane());
            chessGame.getChessBoard().getPane().toBack();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong", ButtonType.OK);
            alert.showAndWait();
            return;
        }
    }

    // Utilisé pour obtenir un dialogue d'ouverture
    public File getOpenFile(String title, String baseDir, Stage stage) {

        fileDialog.setTitle(title);
        fileDialog.setInitialDirectory(new File(System.getProperty("user.dir") + "/" + baseDir));
        return fileDialog.showOpenDialog(stage);
    }

    // Utilisé pour obtenir un dialogue de sauvegarde
    public File getSaveFile(String title, String baseDir, Stage stage) {

        fileDialog.setTitle(title);
        fileDialog.setInitialDirectory(new File(System.getProperty("user.dir") + "/" + baseDir));
        return fileDialog.showSaveDialog(stage);
    }

    public int getBoardPosX() {
        return boardPosX;
    }

    public int getBoardPosY() {
        return boardPosY;
    }
}
