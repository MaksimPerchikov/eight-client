package ru.eightclient;

import java.io.IOException;
import ru.eightclient.handlers.ConnectionHandler;

public class MainApplicationClient {

    public static void main(String[] args) throws IOException {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        connectionHandler.connection();
    }
}
