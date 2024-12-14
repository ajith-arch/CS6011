import java.io.*;

public class WebSocketMessageHandler {

    private  InputStream inputStream ;
    private  OutputStream outputStream ;

    public WebSocketMessageHandler(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void receiveAndEchoMessages() throws IOException {
        while(true){

            //Read the first byte (contains FIN and opcode)
            int finAndOpCode = inputStream.read();

            if(finAndOpCode == -1){
                throw new IOException("Client closed the connection");
            }

            //Read the second byte (contains the MASK and payload length)
            int maskAndPayloadLength = inputStream.read();

            //Determine payload length
            int payloadLength = maskAndPayloadLength & 0x7F;

            //Masking the key(if present)
            byte[] maskingKey = new byte[4] ;
            if((maskAndPayloadLength & 0x80) != 0){
                for(int i=0 ; i<4 ; i++){
                    maskingKey[i] = (byte)inputStream.read();
                }
            }

            //Read playload data
            byte[] payload = new byte[payloadLength];
            for(int i=0 ; i<payloadLength ; i++){
                payload[i] = (byte)inputStream.read();
                if((maskAndPayloadLength & 0x80) != 0){ // Unmasking the payload
                    payload[i] ^= maskingKey[i % 4];
                }
            }
            sendMessage(payload) ;
        }
    }
    public void sendMessage(byte[] message) throws IOException {
        outputStream.write(0x81); // 0x81 = FIN + Text frame
        outputStream.write(message.length);
        outputStream.write(message);
        outputStream.flush();
    }
}
