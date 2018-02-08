package minesweeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Game game = new Game(primaryStage);
        Scene scene = game.getGameScene();
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
//        primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(2));
//        primaryStage.minHeightProperty().bind(scene.widthProperty().divide(2));
//        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
        primaryStage.setOnCloseRequest(e-> {Platform.exit(); System.exit(0);});
    }

    public static void main(String[] args) {
        launch(args);
    }
}
