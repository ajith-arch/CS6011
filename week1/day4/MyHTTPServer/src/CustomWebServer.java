import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class CustomWebServer {
    private final ServerSocket serverSocket;

    public CustomWebServer(int port) throws IOException {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server is running on port: " + port);
        } catch (IOException | IllegalArgumentException ex) {
            System.err.println("Error opening port " + port);
            throw ex;
        }
    }

    public void launch() {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress());

                String httpResponse;
                try {
                    String resource = HTTPRequestHandler.extractResourceName(clientSocket);
                    httpResponse = HTTPResponseBuilder.getResponse(resource);
                } catch (IOException ex) {
                    httpResponse = HTTPResponseBuilder.createErrorResponse("text/plain", "Error processing request.");
                }

                HTTPResponseBuilder.send(clientSocket, httpResponse);

            } catch (IOException ex) {
                System.err.println("Failed to establish a client connection: " + ex.getMessage());
            }
        }
    }
}
