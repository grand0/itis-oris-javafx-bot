package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command;

import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception.CommandExecutionException;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.game.GameApplication;

public class GameCommand extends AbstractCommand {
    @Override
    public String execute() throws CommandExecutionException {
        GameApplication gameApplication = new GameApplication();
        gameApplication.start(stage);
        return "game started";
    }
}
