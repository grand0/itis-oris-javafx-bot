package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command;

import javafx.stage.Stage;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception.CommandExecutionException;

public interface Command {
    String execute() throws CommandExecutionException;
    void init(String[] args, Stage stage);
}
