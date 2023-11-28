package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command;

import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.chat.ChatApplication;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception.CommandExecutionException;

public class ChatCommand extends AbstractCommand {

    @Override
    public String execute() throws CommandExecutionException {
        ChatApplication chatApplication = new ChatApplication();
        chatApplication.start(stage);
        return "chat started";
    }
}
