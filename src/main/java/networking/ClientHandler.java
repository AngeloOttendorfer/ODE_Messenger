package networking;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    /**
     * list of connected clients
     */
    private ArrayList<ClientHandler> clients;

    /**
     * to connect a client to a server
     */
    private Socket socket;

    /**
     * to read incoming messages from the socket
     */
    private BufferedReader reader;

    /**
     * to write messages to the socket which is received by the BufferedReader
     */
    private PrintWriter writer;

    /**
     * initializes socket, an arraylist clients, a BufferedReader and a PrintWriter
     * @param socket is used to connect the client to the server
     * @param clients stores all connected clients
     */
    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients) {
        try {
            this.socket = socket;
            this.clients = clients;
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * thread used for broadcast a message to all connected sockets
     */
    @Override
    public void run() {
        try {
            String msg;

            while ((msg = reader.readLine()) != null) {
                if (msg.equalsIgnoreCase( "exit")) {
                    break;
                }
                for (ClientHandler cl : clients) {
                    cl.writer.println(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
                writer.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}