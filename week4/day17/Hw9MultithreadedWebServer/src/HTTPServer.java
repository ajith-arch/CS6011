import java.io.*;
import java.net.*;

public class HTTPServer {

    private static final int PORT = 8080;

    public static void main(String[] args) {
        System.out.println("Running from dir: " + System.getProperty("user.dir"));
        HTTPServer server = new HTTPServer();
        server.start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Listening for connections on port " + PORT);

            while (true) {
                System.out.println("waiting for client");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Got client");
                ClientHandler RequestHandler = new ClientHandler(clientSocket);
                new Thread(RequestHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
