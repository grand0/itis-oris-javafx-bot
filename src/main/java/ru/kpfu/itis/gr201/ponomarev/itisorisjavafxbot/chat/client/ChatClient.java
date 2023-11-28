package ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.chat.client;

import ru.kpfu.itis.gr201.ponomarev.itisorisjavafxbot.chat.ChatApplication;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ChatClient {

    private final ChatApplication chatApplication;
    private Socket socket;
    private ClientThread thread;

    public ChatClient(ChatApplication chatApplication) {
        this.chatApplication = chatApplication;
    }

    public void sendMessage(String message) {
        try {
            thread.getOutput().write(message);
            thread.getOutput().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        String host = chatApplication.getUserConfig().getHost();
        int port = chatApplication.getUserConfig().getPort();
        try {
            socket = new Socket(host, port);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            thread = new ClientThread(input, output, this);
            new Thread(thread).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ChatApplication getChatApplication() {
        return chatApplication;
    }

    private static class ClientThread implements Runnable {

        private BufferedReader input;
        private BufferedWriter output;
        private ChatClient chatClient;


        public ClientThread(BufferedReader input, BufferedWriter output, ChatClient chatClient) {
            this.input = input;
            this.output = output;
            this.chatClient = chatClient;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println("Waiting for message");
                    String message = input.readLine();
                    System.out.println("Got message '" + message + "'.");
                    chatClient.getChatApplication().appendMessage(message + "\n");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public BufferedWriter getOutput() {
            return output;
        }
    }
}
