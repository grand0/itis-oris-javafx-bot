package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command;

import javafx.stage.Stage;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception.NoSuchCommandException;

import java.util.Arrays;

public class CommandFactory {

    private Stage stage;

    public void init(Stage stage) {
        this.stage = stage;
    }

    public Command createCommand(String commandStr) throws NoSuchCommandException {
        if (stage == null) {
            throw new RuntimeException("CommandFactory is not initialized");
        }
        commandStr = commandStr.trim();
        String commandName = getCommandName(commandStr);
        String[] args = getCommandArgs(commandStr);
        CommandType type = CommandType.getByName(commandName);
        Command command = type.createCommand();
        command.init(args, stage);
        return command;
    }

    private String getCommandName(String commandStr) {
        String[] splitted = commandStr.split("\\s+");
        return splitted[0];
    }

    private String[] getCommandArgs(String commandStr) {
        String[] splitted = commandStr.split("\\s+");
        return Arrays.copyOfRange(splitted, 1, splitted.length);
    }
}
