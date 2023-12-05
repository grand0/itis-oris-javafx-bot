package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.ui;

import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;
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
            getChildren().add(apple);
        }
    }

    public void replacePoisonedApple(Shape poisonedApple) {
        if (this.poisonedApple != null) {
            getChildren().remove(this.poisonedApple);
        }
        this.poisonedApple = poisonedApple;
        if (poisonedApple != null) {
            getChildren().add(poisonedApple);
        }
    }
}
