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
    private final String OPERATORS = "ADD,MULT,SUB,DIV";

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
        try (BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
             BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"))) {

            LOG.log(Level.INFO, "Sent data to client, doing a pause...");
            String welcomeMessage = "Welcome to the calculator server.\n" +
                    "Requiered form is: OPERATION N1 N2 or N1 OPERATION N2\n" + "Valid operations are: " + OPERATORS + "\nPlease enter a command:";
            toClient.write(welcomeMessage, 0, welcomeMessage.length());
            toClient.flush();

            String response = "";
            while (true) {
                String command = fromClient.readLine();
                if (command.equals("exit")) {
                    LOG.log(Level.INFO, "Client disconnected");
                    break;
                }
                LOG.log(Level.INFO, "Received command: " + command);
                response = handleCommand(command) + "\nexit to quit or enter a new command:";
                LOG.log(Level.INFO, "Sending response: " + response);
                toClient.write(response, 0, response.length());
                toClient.flush();

            }
            LOG.log(Level.INFO, "closing server: ");
            response = "closing server bye bye";
            toClient.write(response, 0, response.length());
            toClient.flush();
            clientSocket.close();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String handleCommand(String command) {
        command = command.replace("\n", "");
        String[] commands = command.split(" ");
        if (commands.length != 3) {
            return "Invalid command: syntax can be one of the following: OPERATION N1 N2 or N1 OPERATION N2 \n "
                    + "Valid operation are " + OPERATORS + "\nPlease enter a command:";
        }
        //basic notation
        int n1;
        int n2;
        try {
            n1 = Integer.parseInt(commands[0]);
            n2 = Integer.parseInt(commands[2]);
            if (commands[1].equals("ADD")) {
                return String.valueOf(Integer.parseInt(commands[0]) + Integer.parseInt(commands[2]));
            } else if (commands[1].equals("MULT")) {
                return String.valueOf(Integer.parseInt(commands[0]) * Integer.parseInt(commands[2]));
            } else if (commands[1].equals("DIV")) {
                if (Integer.parseInt(commands[2]) == 0) {
                    return "Division by 0 is not allowed";
                }
                return String.valueOf(Integer.parseInt(commands[0]) / Integer.parseInt(commands[2]));
            } else if (commands[1].equals("SUB")) {
                return String.valueOf(Integer.parseInt(commands[0]) - Integer.parseInt(commands[2]));
            }
        } catch (NumberFormatException e) {

        }
        //Polish notation
        try {
            n1 = Integer.parseInt(commands[1]);
            n2 = Integer.parseInt(commands[2]);
            if (commands[0].equals("ADD")) {
                return String.valueOf(Integer.parseInt(commands[1]) + Integer.parseInt(commands[2]));
            } else if (commands[0].equals("MULT")) {
                return String.valueOf(Integer.parseInt(commands[1]) * Integer.parseInt(commands[2]));
            } else if (commands[0].equals("DIV")) {
                if (Integer.parseInt(commands[2]) == 0) {
                    return "Division by 0 is not allowed";
                }
                return String.valueOf(Integer.parseInt(commands[1]) / Integer.parseInt(commands[2]));
            } else if (commands[0].equals("SUB")) {
                return String.valueOf(Integer.parseInt(commands[1]) - Integer.parseInt(commands[2]));
            }
        } catch (NumberFormatException e) {

        }
        return "Invalid command: syntax can be one of the following: OPERATION N1 N2 or N1 OPERATION N2 \n "
                + "Valid operation are " + OPERATORS + "\nPlease enter a command:";

    }
}













