package ch.heigvd.api.calc;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator client implementation
 */

public class Client {


    private static final Logger LOG = Logger.getLogger(Client.class.getName());
    private static final int BUFFER_SIZE = 1024;
    private static final int PORT = 6969;

    /**
     * Main function to run client
     *
     * @param args no args required
     */

    public static void main(String[] args) {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        final String IP = "";
        BufferedReader stdin = null;
        Socket clientSocket = null;
        try( InputStream fromServer = clientSocket.getInputStream();OutputStream toServer = clientSocket.getOutputStream()) {
            clientSocket = new Socket(IP, PORT);
            toServer.write(IP.getBytes());

            ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int newBytes;
            while ((newBytes = fromServer.read(buffer)) != -1) {
                responseBuffer.write(buffer, 0, newBytes);
            }
            LOG.log(Level.INFO, "Response sent by the server: ");
            LOG.log(Level.INFO, responseBuffer.toString());
        } catch (IOException e) {
            throw new RuntimeException("Error could create a socket form "+IP+" on port "+PORT+ ".\nError:",e);
        }

       

        /* TODO: Implement the client here, according to your specification
         *   The client has to do the following:
         *   - connect to the server
         *   - initialize the dialog with the server according to your specification
         *   - In a loop:
         *     - read the command from the user on stdin (already created)
         *     - send the command to the server
         *     - read the response line from the server (using BufferedReader.readLine)
         */

        stdin = new BufferedReader(new InputStreamReader(System.in));

    }
}
