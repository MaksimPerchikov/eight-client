package ru.eightclient.handlers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Хендлер по подключению и отправлению запросов на сервер
 */
public class ConnectionHandler {

    private final String URL = "http://localhost:8083/";
    private final Scanner scanner;
    private HttpURLConnection connection;

    public ConnectionHandler(){
        scanner = new Scanner(System.in);
    }

    /**
     * Основной метод отправки запроса на сервер
     */
    public void connection() throws IOException {
        while (true){
            String next = scanner.nextLine();
            if(next.equals("stop")){
                this.connection.disconnect();
                return;
            }
            if(!next.isEmpty()){
                createAllParametersForConnection(next);
            }else {
                createAllParametersForConnection(null);
            }
        }
    }

    /**
     * Добавление всех параметров в HttpURLConnection в соответствие с тем, Get или Post метод
     */
    public HttpURLConnection createAllParametersForConnection(String next) throws IOException {
        HttpURLConnection connection = createUrlAndHttpURLConnection();
        String requestMethod = connection.getRequestMethod();
        if(requestMethod.equals("GET") && next.isEmpty()){
            connection.setRequestMethod(requestMethod);
            return connection;
        }else {
            connection.setRequestMethod(requestMethod);
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(this.connection.getOutputStream());
            outputStream.writeBytes(next);
            outputStream.flush();
            outputStream.close();
            getResponseOutputFromServer(this.connection);
            connection.disconnect();
            return connection;
        }
    }

    /**
     * Создаем класс HttpUrlConnection
     */
    private HttpURLConnection createUrlAndHttpURLConnection() throws IOException {
        URL url = new URL(URL);
        return connection = (HttpURLConnection) url.openConnection();
    }

    /**
     * Получаем ответ от сервера
     */
    private void getResponseOutputFromServer(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            System.out.println("Сервер говорит..." + response);
        }
    }

}
