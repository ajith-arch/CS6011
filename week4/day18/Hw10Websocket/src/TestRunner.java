public class TestRunner {
    public static void main(String[] args) {
        try {
            // Test WebSocket handshake
            new WebSocketTest().testWebSocketHandshake();
            System.out.println("WebSocket handshake test passed!");

            // Test WebSocket communication
            new WebSocketCommunicationTest().testWebSocketCommunication();
            System.out.println("WebSocket communication test passed!");

            // Test image response
            new ImageResponseTest().testImageResponse();
            System.out.println("Image response test passed!");
        } catch (AssertionError e) {
            System.err.println("Test failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
