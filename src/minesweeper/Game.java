package minesweeper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game {

    private Scene gameScene;
    private HBox difficultyControllers;
    private HBox customGameSliders;
    private GameManager gameManager;
    private Text text;
    public static final int cellSize = 20;
    private final Stage primaryStage;

    public Game(Stage pStage) {

        primaryStage = pStage;
        initializeButtons();
        initializeSliders();
        gameManager = new GameManager(cellSize * Difficulty.EASY.getNumberOfCells(), Difficulty.EASY, pStage);
        text = new Text();
        text.setFont(new Font(25));
        text.setFill(Color.RED);
        GameTimer.startTimer(text, gameManager);

        VBox gameBox = new VBox();
        HBox gameBoard = new HBox(gameManager.getGameBoard());
        gameBoard.setAlignment(Pos.CENTER);
        gameBox.getChildren().addAll(text,customGameSliders, difficultyControllers, gameBoard);
        gameBox.setAlignment(Pos.CENTER);
        gameBox.setSpacing(5);
        gameScene = new Scene(gameBox);
    }

    private void initializeSliders(){
        Slider cols = new Slider(8, 32, 8);
        Slider rows = new Slider(8, 32, 8);
        Slider bombs = new Slider(10, (int) cols.getValue()*rows.getValue(), 10);

        cols.setShowTickLabels(true); cols.setShowTickMarks(true);
        bombs.setShowTickLabels(true); bombs.setShowTickMarks(true);
        rows.setShowTickLabels(true); rows.setShowTickMarks(true);

        Label colsLbl = new Label("Cols: 8");
        Label rowsLbl = new Label("Rows: 8");
        Label bombsLbl = new Label("Bombs: 10");
        Button newGame = new Button("New Game");

        newGame.setOnAction(e->{
            gameManager.setWidth((int) cols.getValue()*cellSize);
            gameManager.setHeight((int) rows.getValue()*cellSize);
            gameManager.resetGame((int) bombs.getValue());
            GameTimer.startTimer(text, gameManager);
            primaryStage.sizeToScene();
        });

        cols.valueProperty().addListener(e->{colsLbl.textProperty().setValue("Cols: "+String.valueOf((int) cols.getValue()));
        bombs.setMax((int) (cols.getValue()*rows.getValue()));
        });
        rows.valueProperty().addListener(e->{rowsLbl.textProperty().setValue("Rows: "+String.valueOf((int) rows.getValue()));
        bombs.setMax((int) (cols.getValue()*rows.getValue()));
        });
        bombs.valueProperty().addListener(e->bombsLbl.textProperty().setValue("Bombs: "+String.valueOf((int) bombs.getValue())));

        customGameSliders = new HBox();
        customGameSliders.getChildren().addAll(new VBox(colsLbl, cols) , new VBox(rowsLbl, rows), new VBox(bombsLbl, bombs), newGame);
        customGameSliders.setAlignment(Pos.CENTER);
        customGameSliders.setSpacing(5);
        customGameSliders.setPadding(new Insets(0,5,0,5));
    }

    private void initializeButtons() {
        difficultyControllers = new HBox();
        Button[] diffButtons;
        diffButtons = new Button[3];
        diffButtons[0] = new Button("Easy");
        diffButtons[1] = new Button("Medium");
        diffButtons[2] = new Button("Hard");

        diffButtons[0].setOnMouseClicked(e -> buttonAction(Difficulty.EASY));
        diffButtons[1].setOnMouseClicked(e -> buttonAction(Difficulty.MEDIUM));
        diffButtons[2].setOnMouseClicked(e -> buttonAction(Difficulty.HARD));

        difficultyControllers.getChildren().addAll(diffButtons);
        difficultyControllers.setAlignment(Pos.CENTER);
        difficultyControllers.setSpacing(5);
    }

    private void buttonAction(Difficulty diff) {
        gameManager.setWidth(cellSize * diff.getNumberOfCells());
        gameManager.setHeight(cellSize * diff.getNumberOfCells());
        gameManager.resetGame(diff);
        primaryStage.sizeToScene();
        GameTimer.startTimer(text, gameManager);
    }

    public Scene getGameScene() {

        return this.gameScene;
    }
}
