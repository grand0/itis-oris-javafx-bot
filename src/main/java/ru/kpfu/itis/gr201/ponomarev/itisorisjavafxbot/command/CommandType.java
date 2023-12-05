package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command;

import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception.NoSuchCommandException;

import java.util.function.Supplier;

public enum CommandType {
    WEATHER(
            WeatherCommand::new,
            "Show weather for specified city.",
            "/weather <city>",
            "weather"
    ),
    EXCHANGE(
            ExchangeCommand::new,
            "Show exchange rate from specified base currency to other currency.",
            "/exchange <base currency> [currency, default RUB]",
            "exchange"
    ),
    CHAT(
            ChatCommand::new,
            "Open chat window.",
            "/chat",
            "chat"
    ),
    HELP(
            HelpCommand::new,
            "Show info about commands.",
            "/help",
            "help"
    ),
    GAME(
            GameCommand::new,
            "Open game.",
            "/game",
            "game"
    ),
    END(
            EndCommand::new,
            "End session.",
            "/end",
            "end", "exit"
    );

    private final Supplier<Command> commandSupplier;
    private final String description;
    private final String usage;
    private final String[] aliases;

    CommandType(Supplier<Command> commandSupplier, String description, String usage, String... aliases) {
        this.commandSupplier = commandSupplier;
        this.description = description;
        this.usage = usage;
        this.aliases = aliases;
    }

    public static CommandType getByName(String name) throws NoSuchCommandException {
        name = name.trim().toLowerCase();
        for (CommandType ct : values()) {
            for (String alias : ct.getAliases()) {
                if (alias.toLowerCase().equals(name)) {
                    return ct;
                }
            }
        }
        throw new NoSuchCommandException("No such command: " + name + ".");
    }

    public Command createCommand() {
        return getCommandSupplier().get();
    }

    public Supplier<Command> getCommandSupplier() {
        return commandSupplier;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String[] getAliases() {
        return aliases;
    }
}
