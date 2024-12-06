import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            CustomWebServer server = new CustomWebServer(8080);
            server.launch();
        } catch (IOException e) {
            System.err.println("Failed to start the server: " + e.getMessage());
        }
    }
}
