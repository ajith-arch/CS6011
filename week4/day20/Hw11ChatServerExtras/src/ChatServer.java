import java.io.IOException;

/*dsd*/
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer {
    private static final int PORT = 8080;

    /**
     *
     * 
     * Main method to start the chat server.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Chat server started...");
        Room room = new Room();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // Accept a new client connection
                Socket clientSocket = serverSocket.accept();

                System.out.println
                        ("New client connected");

                // Assign the client to a handler and start a new thread
                ClientHandler handler = new ClientHandler(clientSocket, room);

                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }
}
