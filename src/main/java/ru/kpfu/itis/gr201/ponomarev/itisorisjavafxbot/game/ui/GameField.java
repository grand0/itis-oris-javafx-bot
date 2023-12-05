package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.ui;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.Config;

public class GameField extends Pane {

    private Shape apple;
    private Shape poisonedApple;

    public GameField(Shape player) {
        setBackground(Background.fill(Config.THEME.getBackground()));

        getChildren().add(player);
    }

    public void replaceApple(Shape apple) {
        if (this.apple != null) {
            getChildren().remove(this.apple);
        }
        this.apple = apple;
        if (apple != null) {
            apple.setScaleX(0);
            apple.setScaleY(0);
            playAppearingAnimation(apple);
            getChildren().add(apple);
        }
    }

    public void replacePoisonedApple(Shape poisonedApple) {
        if (this.poisonedApple != null) {
            getChildren().remove(this.poisonedApple);
        }
        this.poisonedApple = poisonedApple;
        if (poisonedApple != null) {
            poisonedApple.setScaleY(0);
            poisonedApple.setScaleX(0);
            playAppearingAnimation(poisonedApple);
            getChildren().add(poisonedApple);
        }
    }

    private void playAppearingAnimation(Shape apple) {
        Timeline anim = new Timeline();
        KeyFrame kfStart = new KeyFrame(
                Duration.ZERO,
                new KeyValue(
                        apple.scaleXProperty(),
                        0.0
                ),
                new KeyValue(
                        apple.scaleYProperty(),
                        0.0
                )
        );
        KeyFrame kfEnd = new KeyFrame(
                Duration.millis(300),
                new KeyValue(
                        apple.scaleXProperty(),
                        1.0,
                        Interpolator.EASE_OUT
                ),
                new KeyValue(
                        apple.scaleYProperty(),
                        1.0,
                        Interpolator.EASE_OUT
                )
        );
        anim.getKeyFrames().addAll(kfStart, kfEnd);
        anim.play();
    }
}
