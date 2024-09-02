package java_junior.homework.client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please enter the name of the client: ");
            String name = scanner.nextLine();

            // Установить соединение с сервером
            Socket socket = new Socket("localhost", 1400);

            Client client = new Client(socket, name);
            InetAddress address = socket.getInetAddress();
            System.out.println("Connected to: " + address);

            String remoteIp = address.getHostAddress();
            System.out.println("Remote IP: " + remoteIp);
            System.out.println("Local port: " + socket.getLocalPort());

            // Инициализировать потоки writer & reader
            client.listenForMessages();
            client.sendMessage();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}