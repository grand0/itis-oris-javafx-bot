package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command;

import javafx.stage.Stage;

public abstract class AbstractCommand implements Command {
    protected String[] args;
    protected Stage stage;

    @Override
    public void init(String[] args, Stage stage) {
        this.args = args;
        this.stage = stage;
    }
}
