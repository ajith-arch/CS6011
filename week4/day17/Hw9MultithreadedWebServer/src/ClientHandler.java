import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();


            // create the request handler
            // have it read the headers

            // look at headers to see if you need to use a WS
            // otherwise use response


            // Create a RequestHandler to read the headers and determine the type of request
            RequestHandler requestHandler = new RequestHandler(inputStream);
            requestHandler.readHeaders();

            // Check if the headers indicate a WebSocket upgrade
            if (requestHandler.isWebSocketRequest()) {
                // Handle WebSocket request, passing headers to WebSocketHandler
                WebSocketHandler webSocketHandler = new WebSocketHandler();
                if (webSocketHandler.handleHandshake(requestHandler.getHeaders(), outputStream)) {
                    // Successfully upgraded to WebSocket, proceed with message handling
                    WebSocketMessageHandler messageHandler = new WebSocketMessageHandler(inputStream, outputStream);
                    messageHandler.receiveAndEchoMessages();
                }
            } else {
                // Handle regular HTTP request, passing headers to HTTPRequest
                HTTPRequest request = new HTTPRequest(inputStream, requestHandler.getHeaders());
                HTTPResponse response = new HTTPResponse(outputStream);
                response.handleRequest(request);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing client connection: " + e.getMessage());
            }
        }
    }
}
