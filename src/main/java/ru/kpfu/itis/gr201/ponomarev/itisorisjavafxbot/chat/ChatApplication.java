package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.chat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.chat.client.ChatClient;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.chat.model.UserConfig;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.chat.view.BaseView;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.chat.view.ChatView;
import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.chat.view.UserConfigView;

public class ChatApplication extends Application {

    private UserConfig userConfig;
    private UserConfigView configView;
    private ChatView chatView;
    private BorderPane root;

    private ChatClient chatClient;

    @Override
    public void start(Stage stage) {
        chatClient = new ChatClient(this);

        stage.setTitle("Chat");
        stage.setOnCloseRequest(e -> System.exit(0));

        BaseView.setChatApplication(this);

        configView = new UserConfigView();
        chatView = new ChatView();

        root = new BorderPane();
        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.show();

        setView(configView);
    }

    public void startChat() {
        chatClient.start();
    }

    public void appendMessage(String message) {
        chatView.appendMessage(message);
    }

    public void setView(BaseView view) {
        root.setCenter(view.getView());
    }

    public UserConfig getUserConfig() {
        return userConfig;
    }

    public void setUserConfig(UserConfig userConfig) {
        this.userConfig = userConfig;
    }

    public ChatView getChatView() {
        return chatView;
    }

    public ChatClient getChatClient() {
        return chatClient;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
}
