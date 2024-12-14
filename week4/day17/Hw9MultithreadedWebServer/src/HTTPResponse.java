import java.io.*;
import java.nio.file.*;

public class HTTPResponse {

    private PrintWriter writer;
    private OutputStream outputStream;

    public HTTPResponse(OutputStream outputStream) {
        this.writer = new PrintWriter(outputStream, true);
        this.outputStream = outputStream;
    }

    public void handleRequest(HTTPRequest request) {
        try {
            System.out.println( "handling request");
            String filePath = "../resources" + request.getPath();
            File file = new File(filePath);

            if (file.exists() && !file.isDirectory()) {
                sendFile(file);
            } else {
                send404();
            }
        } catch (UnsupportedOperationException e) {
            send405();
        } catch (Exception e) {
            System.err.println("Error handling request: " + e.getMessage());
            send500();
        }
    }

    private void sendFile(File file) throws Exception {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: " + getContentType(file.getName()));
        writer.println("Content-Length: " + file.length());
        writer.println(); // End of headers
        writer.flush();

        FileInputStream fis = new FileInputStream(file);
        System.out.println( "file size: " + file.length() );
        //fis.transferTo(outputStream);
        for( int i = 0; i < file.length(); i++ ) {
            outputStream.write( fis.read() );
            outputStream.flush();
            Thread.sleep( 0, 100000 );
        }
        System.out.println( "done sending file");
    }

    private void send404() {
        writer.println("HTTP/1.1 404 Not Found");
        writer.println("Content-Type: text/html");
        writer.println();
        writer.println("<html><body><h1>404 Not Found</h1></body></html>");
    }

    private void send405() {
        writer.println("HTTP/1.1 405 Method Not Allowed");
        writer.println("Content-Type: text/html");
        writer.println();
        writer.println("<html><body><h1>405 Method Not Allowed</h1></body></html>");
    }

    private void send500() {
        writer.println("HTTP/1.1 500 Internal Server Error");
        writer.println("Content-Type: text/html");
        writer.println();
        writer.println("<html><body><h1>500 Internal Server Error</h1></body></html>");
    }

    private String getContentType(String fileName) {
        if (fileName.endsWith(".html")) {
            return "text/html";
        } else if (fileName.endsWith(".jpg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else {
            return "application/octet-stream";
        }
    }
}
