import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class HTTPResponseBuilder {
    private static final String TEMPLATE = """
            HTTP/1.1 %s %s
            Content-Type: text/%s
            Connection: close\r\n
            %s
            """;

    public static String createSuccessResponse(String contentType, String body) {
        return String.format(TEMPLATE, "200", "OK", contentType, body);
    }

    public static String createErrorResponse(String contentType, String message) {
        return String.format(TEMPLATE, "404", "Not Found", contentType, message);
    }

    public static String readFileContent(String resourcePath) throws IOException {
        if (resourcePath.equals("/")) {
            resourcePath = "/Users/ajithalphonse/MSD/CS6011/week1/day4/MyHTTPServer/resources/index.html";
        }
        return Files.readString(Path.of("resources" + resourcePath));
    }

    public static String getResponse(String resource) {
        try {
            String content = readFileContent(resource);
            String type = resource.endsWith(".html") ? "html" : "plain";
            return createSuccessResponse(type, content);
        } catch (IOException e) {
            System.err.println("Resource not found: " + resource);
            return createErrorResponse("text/plain", "Requested resource is unavailable.");
        }
    }

    public static void send(Socket client, String response) {
        try (OutputStream output = client.getOutputStream()) {
            output.write(response.getBytes());
            output.flush();
            client.close();
        } catch (IOException e) {
            System.err.println("Failed to send response to client.");
        }
    }
}

