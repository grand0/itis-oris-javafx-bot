package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.ui;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.Config;

public class PointsCounter extends Text {

    private int points;

    public PointsCounter() {
        setFont(Font.font("Consolas", 24));
        setFill(Config.THEME.getTimer());
        redraw();
    }

    private void redraw() {
        setText(String.valueOf(points));
    }

    public void increment() {
        points++;
        redraw();
    }

    public void reset() {
        points = 0;
        redraw();
    }
}
