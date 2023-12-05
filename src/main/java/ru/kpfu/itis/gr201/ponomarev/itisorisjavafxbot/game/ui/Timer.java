package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.ui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.Config;

public class Timer extends Text {

    private final Timeline timeline;
    private int secondsPassed;
    private Runnable callback;

    public Timer() {
        setFont(Font.font("Consolas", 24));
        setFill(Config.THEME.getTimer());

        timeline = new Timeline();
        KeyFrame kf = new KeyFrame(
                Duration.seconds(1),
                event -> {
                    secondsPassed++;
                    redraw();

                    callback.run();
                }
        );
        timeline.getKeyFrames().add(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void redraw() {
        setText(String.valueOf(secondsPassed));
    }

    public void start() {
        timeline.play();
    }

    public void pause() {
        timeline.pause();
    }

    public void stop() {
        timeline.stop();
        secondsPassed = 0;
        redraw();
    }

    public int getSecondsPassed() {
        return secondsPassed;
    }

    public Runnable getCallback() {
        return callback;
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }
}
