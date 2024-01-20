import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class VirtualPrivateNetwork {
    private static final int SERVER_PORT = 8888;

    public static void main(String[] args) {
        // Start the VPN server
        new Thread(() -> startServer()).start();

        // Start the VPN client
        new Thread(() -> startClient()).start();
    }

    private static void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("VPN Server started on port " + SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle client communication in a separate thread
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startClient() {
        try {
            Socket clientSocket = new Socket("localhost", SERVER_PORT);
            System.out.println("Connected to VPN server");

            // Handle server communication in a separate thread
            new Thread(() -> handleServer(clientSocket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter serverWriter = new PrintWriter(clientSocket.getOutputStream(), true);

            while (true) {
                // Read data from the client
                String clientData = clientReader.readLine();
                if (clientData == null) {
                    break; // Connection closed
                }

                // Process data (e.g., encrypt/decrypt, route, etc.)

                // Send data to the server
                serverWriter.println("Server: " + clientData);
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleServer(Socket serverSocket) {
        try {
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            PrintWriter clientWriter = new PrintWriter(serverSocket.getOutputStream(), true);

            while (true) {
                // Read data from the server
                String serverData = serverReader.readLine();
                if (serverData == null) {
                    break; // Connection closed
                }

                // Process data (e.g., encrypt/decrypt, route, etc.)

                // Send data to the client
                clientWriter.println("Client: " + serverData);
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
