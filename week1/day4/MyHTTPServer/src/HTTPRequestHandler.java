import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

public class HTTPRequestHandler {
    public static String parseRequestLine(Scanner scanner) {
        String line = scanner.nextLine();
        return line.split(" ")[1]; // Extract the resource path
    }

    public static String extractResourceName(Socket socket) throws IOException {
        try (InputStream input = socket.getInputStream(); Scanner scanner = new Scanner(input)) {
            return parseRequestLine(scanner);
        } catch (IOException e) {
            System.err.println("Error parsing the incoming request.");
            throw e;
        }
    }
}

