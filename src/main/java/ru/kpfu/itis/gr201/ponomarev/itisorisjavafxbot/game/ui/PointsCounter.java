package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.ui;

import javafx.scene.text.Text;

public class PointsCounter extends Text {

    private int points;

    public PointsCounter() {
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
