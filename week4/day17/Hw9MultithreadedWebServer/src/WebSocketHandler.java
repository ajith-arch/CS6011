import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

public class WebSocketHandler {

    private static final String WEBSOCKET_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

    // Perform the WebSocket handshake using the headers from RequestHandler
    public boolean handleHandshake(HashMap<String, String> headers, OutputStream outputStream) throws IOException, NoSuchAlgorithmException {
        PrintWriter writer = new PrintWriter(outputStream);

        // Get the Sec-WebSocket-Key from the client's request headers
        String webSocketKey = headers.get("Sec-WebSocket-Key");
        if (webSocketKey == null) {
            return false;
        }

        // Generate Sec-WebSocket-Accept
        String acceptKey = generateWebSocketAcceptKey(webSocketKey);

        // Send the handshake response to the client
        writer.println("HTTP/1.1 101 Switching Protocols");
        writer.println("Upgrade: websocket");
        writer.println("Connection: Upgrade");
        writer.println("Sec-WebSocket-Accept: " + acceptKey);
        writer.println(); // End of headers
        writer.flush();

        return true;
    }

    // Generate the Sec-WebSocket-Accept key using SHA-1 and Base64 encoding
    private String generateWebSocketAcceptKey(String webSocketKey) throws NoSuchAlgorithmException {
        String acceptSeed = webSocketKey + WEBSOCKET_GUID;
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] hash = md.digest(acceptSeed.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
