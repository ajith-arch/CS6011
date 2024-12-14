import java.io.*;
import java.util.*;

public class Room {
    private final Set<PrintWriter> clients = new HashSet<>();

    // Synchronized method to add a new client to the room
    public synchronized void addClient(PrintWriter out) {
        clients.add(out);
    }

    // Synchronized method to broadcast a message to all clients
    public synchronized void broadcast(String message, PrintWriter sender) {
        for (PrintWriter client : clients) {
            if (client != sender) { // Avoid echoing back to the sender
                client.println(message);
                client.flush();
            }
        }
    }
}
