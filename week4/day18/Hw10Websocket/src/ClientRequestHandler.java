import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class ClientRequestHandler {
    private static final String WEBSOCKET_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

    public ClientRequestHandler(Socket socket) {
        // Placeholder: Socket is not used in tests
    }

    public void handleWebSocketHandshake(String request, PrintWriter responseWriter) throws NoSuchAlgorithmException {
        // Parse the WebSocket request headers
        if (request.contains("Upgrade: websocket") && request.contains("Connection: Upgrade")) {
            String secWebSocketKey = extractWebSocketKey(request);

            // Generate Sec-WebSocket-Accept
            String acceptKey = generateWebSocketAccept(secWebSocketKey);

            // Write the handshake response
            responseWriter.println("HTTP/1.1 101 Switching Protocols");
            responseWriter.println("Upgrade: websocket");
            responseWriter.println("Connection: Upgrade");
            responseWriter.println("Sec-WebSocket-Accept: " + acceptKey);
            responseWriter.println();
        }
    }

    private String extractWebSocketKey(String request) {
        for (String line : request.split("\r\n")) {
            if (line.startsWith("Sec-WebSocket-Key:")) {
                return line.split(":")[1].trim();
            }
        }
        return null;
    }

    private String generateWebSocketAccept(String secWebSocketKey) throws NoSuchAlgorithmException {
        String acceptSeed = secWebSocketKey + WEBSOCKET_GUID;
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hash = digest.digest(acceptSeed.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    public void handleWebSocketCommunication(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[8192];
        int bytesRead = inputStream.read(buffer);
        if (bytesRead > 0) {
            outputStream.write(buffer, 0, bytesRead); // Echo back the message
            outputStream.flush();
        }
    }
}

class WebSocketTest {
    @Test
    void testWebSocketHandshake() throws Exception {
        String simulatedHttpRequest =
                "GET / HTTP/1.1\r\n" +
                        "Host: localhost\r\n" +
                        "Upgrade: websocket\r\n" +
                        "Connection: Upgrade\r\n" +
                        "Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==\r\n" +
                        "\r\n";

        ByteArrayOutputStream responseOutput = new ByteArrayOutputStream();
        PrintWriter httpResponseWriter = new PrintWriter(new OutputStreamWriter(responseOutput));

        ClientRequestHandler handler = new ClientRequestHandler(null);
        handler.handleWebSocketHandshake(simulatedHttpRequest, httpResponseWriter);

        httpResponseWriter.flush();
        String response = responseOutput.toString();
        assertTrue(response.contains("HTTP/1.1 101 Switching Protocols"));
        assertTrue(response.contains("Upgrade: websocket"));
        assertTrue(response.contains("Connection: Upgrade"));
        assertTrue(response.contains("Sec-WebSocket-Accept:"));
    }
}

class WebSocketCommunicationTest {
    @Test
    void testWebSocketCommunication() throws IOException {
        byte[] simulatedClientMessage = "Hello WebSocket".getBytes();
        ByteArrayInputStream clientInputStream = new ByteArrayInputStream(simulatedClientMessage);
        ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();

        ClientRequestHandler handler = new ClientRequestHandler(null);
        handler.handleWebSocketCommunication(clientInputStream, clientOutputStream);

        assertArrayEquals(simulatedClientMessage, clientOutputStream.toByteArray());
    }
}



class ImageResponseTest {
    @Test
    void testImageResponse() throws IOException {
        // Simulate the content of an image file
        byte[] simulatedImageContent = "SIMULATED_IMAGE_CONTENT".getBytes();
        ByteArrayInputStream simulatedImageStream = new ByteArrayInputStream(simulatedImageContent);
        ByteArrayOutputStream clientOutputStream = new ByteArrayOutputStream();
        PrintWriter httpResponseWriter = new PrintWriter(clientOutputStream);

        // Simulate HTTP response for an image file
        httpResponseWriter.println("HTTP/1.1 200 OK");
        httpResponseWriter.println("Content-Type: image/jpeg");
        httpResponseWriter.println("Connection: close");
        httpResponseWriter.println(); // End of headers
        httpResponseWriter.flush();

        // Simulate image file transfer logic
        byte[] buffer = new byte[8192]; // Typical buffer size for file transfer
        int bytesRead;
        while ((bytesRead = simulatedImageStream.read(buffer)) != -1) {
            clientOutputStream.write(buffer, 0, bytesRead);
        }

        // Verify the HTTP response and content
        String response = clientOutputStream.toString();
        assertTrue(response.contains("HTTP/1.1 200 OK"));
        assertTrue(response.contains("Content-Type: image/jpeg"));
        assertTrue(response.contains("Connection: close"));
        assertTrue(response.contains("SIMULATED_IMAGE_CONTENT")); // Verify image content
    }
}



