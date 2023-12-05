package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.ui;

import javafx.animation.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Timer extends Text {

    private Timeline emphasizeAnimation;

    private final Timeline timeline;
    private int secondsPassed;
    private Runnable callback;

    public Timer() {
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
        setText(formattedTimePassed());
    }

    private String formattedTimePassed() {
        return String.format(
                "%02d:%02d",
                secondsPassed / 60,
                secondsPassed % 60
        );
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

    public void playEmphasizeAnimation() {
        if (emphasizeAnimation == null) {
            emphasizeAnimation = new Timeline();
            KeyFrame kfScaleUp = new KeyFrame(
                    new Duration(500),
                    new KeyValue(
                            scaleXProperty(),
                            2,
                            Interpolator.EASE_OUT
                    ),
                    new KeyValue(
                            scaleYProperty(),
                            2,
                            Interpolator.EASE_OUT
                    )
            );
            KeyFrame kfScaleDown = new KeyFrame(
                    new Duration(1000),
                    new KeyValue(
                            scaleXProperty(),
                            1,
                            Interpolator.EASE_IN
                    ),
                    new KeyValue(
                            scaleYProperty(),
                            1,
                            Interpolator.EASE_IN
                    )
            );
            emphasizeAnimation.getKeyFrames().addAll(kfScaleUp, kfScaleDown);
        }
        emphasizeAnimation.playFromStart();
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
