package ch.heigvd.api.calc;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator server implementation - single threaded
 */

public class Server {
    private static final int BUFFER_SIZE = 1024;


    private static final int PORT = 420;


    private final static Logger LOG = Logger.getLogger(Server.class.getName());

    /**
     * Main function to start the server
     */
    public static void main(String[] args) {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");
        (new Server()).start();
    }

    /**
     * Start the server on a listening socket.
     */
    private void start() {
        /* TODO: implement the receptionist server here.
         *  The receptionist just creates a server socket and accepts new client connections.
         *  For a new client connection, the actual work is done by the handleClient method below.
         * TODO cedric review
         */

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LOG.log(Level.INFO, "Server started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                LOG.log(Level.INFO, "Client connected from " + clientSocket.getInetAddress());
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error could not create a socket on port " + PORT + ".\nError:", e);
        }


    }

    /**
     * Handle a single client connection: receive commands and send back the result.
     *
     * @param clientSocket with the connection with the individual client.
     */
    private void handleClient(Socket clientSocket) {

        /* TODO: implement the handling of a client connection according to the specification.
         *   The server has to do the following:
         *   - initialize the dialog according to the specification (for example send the list
         *     of possible commands)
         *   - In a loop:
         *     - Read a message from the input stream (using BufferedReader.readLine)
         *     - Handle the message
         *     - Send to result to the client
         *  TODO cedric review
         */

        try (BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
             BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"))) {

            LOG.log(Level.INFO, "Sent data to client, doing a pause...");
            String message = "Welcome to the calculator server. Please enter a command: ";
            toClient.write(message, 0, message.length());
            toClient.flush();

            ByteArrayOutputStream responseBuffer = new ByteArrayOutputStream();

            while (fromClient.read() != -1) {
                String command = fromClient.readLine();
                LOG.log(Level.INFO, "Received command: " + command);
                //TODO DEBUG si on ecrit 123 command vaut 23
                String response = handleCommand(command);
                LOG.log(Level.INFO, "Sending response: " + response);
                toClient.write(response, 0, response.length());
                toClient.flush();

            }
            LOG.log(Level.INFO, "closing server: ");
            clientSocket.close();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String handleCommand(String command) {
        return "Command not implemented";
    }


}













