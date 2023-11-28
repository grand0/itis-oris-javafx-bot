package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.chat.view;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import static javafx.scene.input.KeyEvent.KEY_PRESSED;

public class ChatView extends BaseView {

    private TextArea input;
    private TextArea conversation;
    private AnchorPane pane;

    private final EventHandler<KeyEvent> eventHandler = event -> {
        if (event.getCode() == KeyCode.ENTER) {
            String username = getChatApplication().getUserConfig().getUsername();
            String message = input.getText() + "\n";
            // send message
            getChatApplication().getChatClient().sendMessage(username + ": " + message);
            conversation.appendText(getChatApplication().getUserConfig().getUsername() + ": " + message);
            input.clear();
            event.consume();
        }
    };

    @Override
    public Parent getView() {
        if (pane == null) {
            createView();
        }
        return pane;
    }

    private void createView() {
        pane = new AnchorPane();

        conversation = new TextArea();
        conversation.setEditable(false);
        conversation.setWrapText(true);
        AnchorPane.setTopAnchor(conversation, 60.0);

        input = new TextArea();
        input.setMaxHeight(50);
        input.addEventHandler(KEY_PRESSED, eventHandler);

        pane.getChildren().addAll(input, conversation);
    }

    public void appendMessage(String message) {
        if (message != null) {
            conversation.appendText(message);
        }
    }
}
