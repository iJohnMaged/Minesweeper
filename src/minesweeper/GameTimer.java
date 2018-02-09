package minesweeper;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GameTimer {

    private static Timeline timeLine;
    private static int seconds = 0;

    public static void startTimer(Text textarea, GameManager gameManager) {
        if (timeLine == null){
            timeLine = new Timeline(new KeyFrame(Duration.ZERO, ae -> {
                if (gameManager.hasGameEnded()) {
                    destroyTimer();
                    return;
                }
                textarea.setText(
                        "Time: " + LocalTime.ofSecondOfDay(++seconds).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }), new KeyFrame(Duration.millis(1000)));
            timeLine.setCycleCount(Animation.INDEFINITE);
            timeLine.setDelay(Duration.ZERO);
        }
        else{
            destroyTimer();
        }

        timeLine.play();
    }

    public static void destroyTimer() {
        timeLine.stop(); seconds = 0;
    }
}
