import java.io.*;
import java.util.HashMap;

public class HTTPRequest {

    private HashMap<String, String> headers;
    private String method;
    private String path;
    private String version;

    public HTTPRequest(InputStream inputStream, HashMap<String, String> headers) throws IOException {
        this.headers = headers;
        parseRequestLine(inputStream);
    }

    private void parseRequestLine(InputStream inputStream)   {
        // Assuming we parse only the first line for method, path, and version
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String requestLine="";
        try {
              requestLine =reader.readLine();

        }catch (IOException e){
            e.printStackTrace();
        }
        if (requestLine != null && !requestLine.isEmpty()) {
            String[] requestParts = requestLine.split(" ");
            if (requestParts.length == 3) {
                method = requestParts[0];
                path = requestParts[1];
                version = requestParts[2];
            }
        }
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public String getVersion() {
        return version;
    }
}
