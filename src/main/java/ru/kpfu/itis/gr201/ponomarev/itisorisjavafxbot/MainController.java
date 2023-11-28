package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command.Command;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.command.CommandFactory;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception.CommandExecutionException;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.exception.NoSuchCommandException;

public class MainController {
    @FXML
    private TextArea chatArea;

    @FXML
    private TextField commandField;

    private static final CommandFactory COMMAND_FACTORY;

    private boolean botStarted = false;

    static {
        COMMAND_FACTORY = new CommandFactory();
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            COMMAND_FACTORY.init((Stage) commandField.getScene().getWindow());
            commandField.requestFocus();
        });
    }

    @FXML
    private void onCommandFieldKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String msg = commandField.getText().trim();
            if (!msg.isEmpty()) {
                chatArea.appendText("you: " + msg + "\n");
                if (msg.equals("/start")) { // special command
                    if (!botStarted) {
                        botStarted = true;
                        chatArea.appendText("bot started!\n");
                    } else {
                        chatArea.appendText("bot already started.\n");
                    }
                } else if (msg.startsWith("/")) {
                    if (!botStarted) {
                        chatArea.appendText("bot haven't been started.\n");
                    } else {
                        commandField.setDisable(true);
                        String response = executeCommand(msg.substring(1));
                        commandField.setDisable(false);
                        chatArea.appendText("bot: " + response + "\n");
                    }
                }
            }
            commandField.clear();
        }
    }

    private String executeCommand(String commandStr) {
        try {
            Command command;
            command = COMMAND_FACTORY.createCommand(commandStr);
            return command.execute();
        } catch (NoSuchCommandException | CommandExecutionException e) {
            return e.getMessage();
        }
    }
}
