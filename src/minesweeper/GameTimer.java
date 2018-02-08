package minesweeper;

import javafx.scene.text.Text;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {

    private static Timer timer;

    public static void startTimer(Text textarea, GameManager gameManager) {
        if (timer != null)
            destroyTimer();
        timer = new Timer();

        TimerTask task = new TimerTask() {
            int seconds = 0;

            @Override
            public void run() {
                if (gameManager.hasGameEnded()) {
                    destroyTimer();
                    return;
                }
                textarea.setText(
                        "Time: " + LocalTime.ofSecondOfDay(++seconds).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        };

        timer.schedule(task, 0, 1000L);
    }

    public static void destroyTimer() {
        timer.cancel();
    }
}
