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
    private static final String IP = "localhost";
    private static final int PORT = 420;

    /**
     * Main function to run client
     *
     * @param args no args required
     */

    public static void main(String[] args) {
            // Log output on a single line
                System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

                Socket clientSocket = null;
                try {
                    clientSocket = new Socket(IP, PORT);
                    InputStream fromServer = clientSocket.getInputStream();
                    OutputStream toServer = clientSocket.getOutputStream();

                    LOG.log(Level.INFO, "Response sent by the server: ");
                    LOG.log(Level.INFO, fromServer.toString());

                    //write to server from console
                    String input = "";
                    while(true){
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        input = br.readLine();

                        if(input.equals("exit"))break;
                        //TODO le premier caractere est pas lu donc j'ai ajouer un \n
                        input = "\n" + input + "\n";

                        toServer.write(input.getBytes());
                        toServer.flush();
                    }
                    clientSocket.close();
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
    }
}
