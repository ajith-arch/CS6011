import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final Room room;

    public ClientHandler(Socket socket, Room room) {
        this.clientSocket = socket;
        this.room = room;
    }

    @Override
    public void run() {
        try (
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                PrintWriter writer = new PrintWriter(outputStream, true);
        ) {
            // Add the client to the room
            room.addClient(writer);

            // Notify the room that a new client has joined
            room.broadcast("A new user has joined the chat!", writer);

            // Read messages from the client and broadcast them
            String message;
            while ((message = reader.readLine()) != null) {
                room.broadcast(message, writer);
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client socket: " + e.getMessage());
            }
        }
    }
}
