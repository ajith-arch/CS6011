import org.junit.jupiter.api.Test;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketTest {

    @Test
    public void testWebSocketHandshake() throws IOException, NoSuchAlgorithmException {
        // Set up the headers to mimic a WebSocket upgrade request
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");
        headers.put("Upgrade", "websocket");
        headers.put("Connection", "Upgrade");
        headers.put("Sec-WebSocket-Key", "dGhlIHNhbXBsZSBub25jZQ=="); // Example key from the spec
        headers.put("Sec-WebSocket-Version", "13");

        // Use ByteArrayOutputStream to capture the server's response
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Initialize WebSocketHandler and perform handshake
        WebSocketHandler webSocketHandler = new WebSocketHandler();
        boolean handshakeSuccess = webSocketHandler.handleHandshake(headers, outputStream);

        // Verify handshake was successful
        assertTrue(handshakeSuccess, "The handshake should succeed for a valid WebSocket request");

        // Verify the server's response contains the expected WebSocket headers
        String response = outputStream.toString();
        assertTrue(response.contains("HTTP/1.1 101 Switching Protocols"), "Response should indicate a successful protocol switch");
        assertTrue(response.contains("Upgrade: websocket"), "Response should contain 'Upgrade: websocket' header");
        assertTrue(response.contains("Connection: Upgrade"), "Response should contain 'Connection: Upgrade' header");
        assertTrue(response.contains("Sec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo="), "Response should contain the correct Sec-WebSocket-Accept header");
    }

    @Test
    public void testInvalidWebSocketHandshake() throws IOException, NoSuchAlgorithmException {
        // Set up invalid headers (missing required fields)
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");

        // Use ByteArrayOutputStream to capture the server's response
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Initialize WebSocketHandler and attempt handshake
        WebSocketHandler webSocketHandler = new WebSocketHandler();
        boolean handshakeSuccess = webSocketHandler.handleHandshake(headers, outputStream);

        // Verify handshake fails
        assertFalse(handshakeSuccess, "The handshake should fail for an invalid WebSocket request");

        // Verify the response does not indicate a protocol switch
        String response = outputStream.toString();
        assertFalse(response.contains("HTTP/1.1 101 Switching Protocols"), "Response should not indicate a protocol switch");
    }

    @Test
    public void testSecWebSocketAcceptGeneration() throws NoSuchAlgorithmException {
        // Example key from the WebSocket specification
        String secWebSocketKey = "dGhlIHNhbXBsZSBub25jZQ==";
        String expectedAccept = "s3pPLMBiTxaQ9kYGzzhZRbK+xOo=";

        // Generate Sec-WebSocket-Accept value using SHA-1 and Base64
        String concatenated = secWebSocketKey + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        byte[] hashed = digest.digest(concatenated.getBytes());
        String generatedAccept = Base64.getEncoder().encodeToString(hashed);

        // Verify the generated except value matches the expected value
        assertEquals(expectedAccept, generatedAccept, "Generated Sec-WebSocket-Accept value should match the expected value");
    }
}
