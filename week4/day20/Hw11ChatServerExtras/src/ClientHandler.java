import java.io.*;

import java.net.Socket;


public class ClientHandler implements Runnable {
    private final Socket
            clientSocket;
    private final Room room;

    /**
     *
     *
     * Constructs a ClientHandler object.
     *
     * @param socket The client's socket connection.
     * @param room   The chat room that the client has joined.
     */
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
            // Add client to the room
            room.addClient(writer);

            // Notify the room that a new client has joined
            room.broadcast("A new user has joined the chat!", writer);

            // Read and handle messages from the client
            String message;


            while ((message = reader.readLine()) != null) {
                if (message.equalsIgnoreCase("/clients")) {
                    writer.println("Number of clients in the room: " + room.getClientCount());
                } else {
                    room.broadcast(message, writer);
                }
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
