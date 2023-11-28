package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command;

import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception.CommandExecutionException;

public class HelpCommand extends AbstractCommand {

    @Override
    public String execute() throws CommandExecutionException {
        StringBuilder sb = new StringBuilder();
        for (CommandType ct : CommandType.values()) {
            sb.append(ct.getUsage())
                    .append(" - ")
                    .append(ct.getDescription())
                    .append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
