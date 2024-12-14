import java.io.*;
import java.util.HashMap;

public class RequestHandler {

    private HashMap<String, String> headers = new HashMap<>();
    private InputStream inputStream;

    public RequestHandler(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public void readHeaders() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        // Read headers line by line
        while (!(line = reader.readLine()).isEmpty()) {
            if (line.contains(":")) {
                String[] header = line.split(": ", 2);
                headers.put(header[0].trim(), header[1].trim());
            }
        }
    }

    // Determine if the request is a WebSocket upgrade request
    public boolean isWebSocketRequest() {
        return headers.containsKey("Upgrade") && headers.get("Upgrade").equalsIgnoreCase("websocket");
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    // Log all headers to the console
    public void logHeaders() {
        System.out.println("Request Headers:");
        for (String key : headers.keySet()) {
            System.out.println(key + ": " + headers.get(key));
        }
    }

    // Retrieve a specific header value
    public String getHeader(String headerName) {
        return headers.getOrDefault(headerName, "Header not found");
    }

    public static void main(String[] args) throws IOException {
        // Simulate input stream for testing
        String simulatedInput = "Host: example.com\n"
                + "Upgrade: websocket\n"
                + "Connection: Upgrade\n"
                + "Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==\n"
                + "Sec-WebSocket-Version: 13\n\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());

        // Create RequestHandler instance
        RequestHandler handler = new RequestHandler(inputStream);

        // Read and log headers
        handler.readHeaders();
        handler.logHeaders();

        // Check if it's a WebSocket request
        System.out.println("Is WebSocket Request: " + handler.isWebSocketRequest());

        // Get a specific header
        System.out.println("Host Header: " + handler.getHeader("Host"));
    }
}

