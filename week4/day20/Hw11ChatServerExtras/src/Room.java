import java.io.*;
import java.text.SimpleDateFormat;




import java.util.*;

/**
 * The Room class represents a chat room that manages connected clients
 * and handles broadcasting messages to all cliets in the room.
 *
 *
 *
 * It also persists messages to disk and loads previous messaes on startup.
 */
public class Room {
    private final Set<PrintWriter> clients = new HashSet<>();
    private final String logFilePath = "room_logs.txt";

    /**
     * Constructs a Room object an   loads any existing messages from the log file.
     */
    public Room() {
        loadMessages();
    }

    /**
     * Adds a client to the room.
     *
     * @param out The PrintWriter object associated with the client.
     */
    public synchronized void addClient(PrintWriter out) {
        clients.add(out);
    }

    /**
     * Broadcasts a message to all cl/ients in the room.
     *
     * @param message The messa//ge to broadcast.
     * @param sender  The PrintWriter of the client who sent the message.
     */
    public synchronized void broadcast(String message, PrintWriter sender) {
        String timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String formattedMessage = "[" + timestamp + "] " + message;

        // Broadcast to all clients
        for (PrintWriter client : clients) {
            if (client != sender) {
                client.println(formattedMessage);
                client.flush();
            }
        }

        // Save message to the log file
        saveMessage(formattedMessage);
    }

    /**
     * Returns the numbe/r of active clients in the room.
     *
     * @return The number of connected clients.
     */
    public synchronized int getClientCount() {
        return clients.size();
    }

    /**
     * Loads previous messages from the log file and sends them to new clients.
     */
    private void loadMessages() {
        File logFile = new File(logFilePath);
        if (!logFile.exists()) {




            try {
                logFile.createNewFile(); // Create the file if it doesn't exist
                System.out.println("Log file created: " + logFilePath);
            } catch (IOException e) {
                System.err.println("Error creating log file: " + e.getMessage());
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String message;



            while ((message = reader.readLine()) != null) {
                for (PrintWriter client : clients) {
                    client.println(message);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading log file: " + e.getMessage());
        }
    }


    /**
     * Saves a message to t\he log file.
     *
     * @param message The message to save.
     */
    private void saveMessage(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFilePath, true))) {
            writer.println(message);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
