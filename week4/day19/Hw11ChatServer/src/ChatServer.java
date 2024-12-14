import java.io.*;
import java.net.*;

public class ChatServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        System.out.println("Chat server started...");
        Room room = new Room();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");
                ClientHandler handler = new ClientHandler(clientSocket, room);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }
}
