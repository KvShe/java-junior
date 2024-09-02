package java_junior.homework.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientManager implements Runnable {
    private Socket socket;
    private String name;
    private BufferedReader reader;
    private BufferedWriter writer;

    public final static List<ClientManager> clients = new ArrayList<>();

    public ClientManager(Socket socket) {
        try {
            this.socket = socket;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.name = reader.readLine();
            clients.add(this);

            System.out.println(this.name + " connected");
            broadcast("Server: " + name + " connected");
        } catch (IOException e) {
            closeEverything();
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                String messageFromClient = reader.readLine();
                broadcast(messageFromClient);
            } catch (IOException e) {
                closeEverything();
                break;
            }
        }
    }

    private void broadcast(String message) {
        for (ClientManager client : clients) {
            if (client.name.equals(name)) continue;
            try {
                client.writer.write(message);
                client.writer.newLine();
                client.writer.flush();
            } catch (IOException e) {
                closeEverything();
            }
        }
    }

    private void closeEverything() {
        removeClient();

        try {
            if (reader != null) reader.close();
            if (writer != null) writer.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeClient() {
        clients.remove(this);
        System.out.println(name + " disconnected");
        broadcast(name + " disconnected");
    }
}
