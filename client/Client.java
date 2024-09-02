package java_junior.homework.client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Socket socket;
    private final String name;
    private BufferedWriter writer;
    private BufferedReader reader;

    public Client(Socket socket, String name) {
        this.socket = socket;
        this.name = name;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            closeEverything();
        }
    }

    public void listenForMessages() {
        new Thread(() -> {
            String message;

            while (socket.isConnected()) {
                try {
                    message = reader.readLine();
                    System.out.println(message);
                } catch (IOException e) {
                    closeEverything();
                }
            }
        }).start();
    }

    public void sendMessage() {
        sendMessage(name);

        Scanner scanner = new Scanner(System.in);
        while (socket.isConnected()) {
            String message = scanner.nextLine();
            sendMessage(name + ": " + message);
        }
    }

    private void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            closeEverything();
        }
    }

    private void closeEverything() {
        try {
            if (writer != null) writer.close();
            if (reader != null) reader.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
