package minesweeper;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Game {

    private Scene gameScene;
    private HBox difficultyControllers;
    private GameManager gameManager;
    private Text text;
    private static final int WIDTH = 720;

    public Game() {

        initializeButtons();
        gameManager = new GameManager(WIDTH, Difficulty.EASY);
        text = new Text();
        text.setFont(new Font(25));
        text.setFill(Color.RED);
        GameTimer.startTimer(text, gameManager);

        VBox gameBox = new VBox();
        gameBox.getChildren().addAll(text, difficultyControllers, gameManager.getGameBoard());
        gameBox.setAlignment(Pos.CENTER);
        gameBox.setSpacing(5);
        gameScene = new Scene(gameBox);
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
        gameManager.newGame(diff);
        GameTimer.startTimer(text, gameManager);
    }

    public Scene getGameScene() {
        return this.gameScene;
    }
}
