package controller;

import com.example.messenger.MessengerApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class ChatController extends Thread implements Initializable {
    /**
     * text-field contains the message to send to other clients
     */
    @FXML
    private TextField tf_msg;

    /**
     * the profile username
     */
    @FXML
    private Label lbl_username;

    /**
     * textArea for chat messages
     */
    @FXML
    private TextArea ta_chat;

    /**
     * the logout button to return to the welcome screen
     */
    @FXML
    private Button btn_logout;

    /**
     * reads incoming messages from other clients
     */
    BufferedReader reader;

    /**
     * writes messages that is read by the BufferedReader
     */
    PrintWriter writer;

    /**
     * socket for client-server connection
     */
    Socket socket;

    /**
     * connect to the server by passing in the host and the port number in the socket instance
     * create a BufferedReader and PrintWriter in order to send messages to all connected clients
     * which is performed by a thread
     */
    public void connectSocket() {
        try {
            socket = new Socket("localhost", 3000);
            System.out.println("Socket is connected with server!");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * thread method which sends the received message to all connected clients
     */
    @Override
    public void run() {
        try {
            while (true) {
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];
                System.out.println(cmd);
                StringBuilder fulmsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    fulmsg.append(tokens[i]);
                }
                System.out.println(fulmsg);
                if (cmd.equalsIgnoreCase(LoginController.username + ":")) {
                    continue;
                } else if (fulmsg.toString().equalsIgnoreCase("bye")) {
                    break;
                }
                ta_chat.appendText(msg + "\n");
            }
            reader.close();
            writer.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * send the message to all clients if the send image is pressed
     */
    @FXML
    public void handleSendEvent(MouseEvent event) {
        send();
    }

    /**
     * send the message to all clients if the enter key is pressed
     * @param event enter key
     */
    @FXML
    public void sendMessageByKey(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            send();
        }
    }

    /**
     * gets the text in the text-field to send the message with the PrintWriter in a specific format
     * if "bye" or "logout" is sent (ignoring case considerations) as a message the application is turned off
     */
    public void send() {
        String msg = tf_msg.getText();
        if(!msg.isEmpty()){
            writer.println(LoginController.username + ": " + msg);
            ta_chat.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            ta_chat.appendText("Me: " + msg + "\n");
            tf_msg.setText("");
        }
        if(msg.equalsIgnoreCase("BYE") || (msg.equalsIgnoreCase("logout"))) {
            System.exit(0);
        }
    }

    /**
     * returning to the welcomeScreen if the logout of the chatScreen GUI is pressed
     * @throws IOException
     */
    public void logout() throws IOException {
        FXMLLoader welcomeLoader = new FXMLLoader(MessengerApplication.class.getResource("welcomeScreen.fxml"));
        Stage login = (Stage) btn_logout.getScene().getWindow();
        login.setResizable(false);
        login.setScene(new Scene(welcomeLoader.load()));
    }

    /**
     * sets the current username from the loginScreen for the chat-GUI
     */
    public void setUserLabel(){
        this.lbl_username.setText(LoginController.username);
    }

    /**
     * calls the setUserLabel and connectSocket method as soon as the chat-GUI is loaded
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUserLabel();
        connectSocket();
    }
}
