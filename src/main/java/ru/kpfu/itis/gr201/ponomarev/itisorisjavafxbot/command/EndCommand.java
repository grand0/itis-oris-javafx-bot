package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command;

import javafx.application.Platform;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception.CommandExecutionException;

public class EndCommand extends AbstractCommand {

    @Override
    public String execute() throws CommandExecutionException {
        Platform.exit();
        return "";
    }
}
