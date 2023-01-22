package controller;

import com.example.messenger.MessengerApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import networking.Server;

import java.io.IOException;

public class LoginController {
    /**
     * text-field for username login
     */
    @FXML
    private TextField tf_username;

    /**
     * password field for login
     */
    @FXML
    private PasswordField pf_pwd;

    /**
     * button to return to the welcomeScreen
     */
    @FXML
    private Button btn_return;

    /**
     * initializing the server to check for valid login data using the containsUserData method
     */
    private final Server server = new Server();

    /**
     * the username to be logged in with
     */
    public static String username;

    /**
     * the password to be logged in with
     */
    public String password;

    /**
     * this function is called whenever btn_login (login button in the GUI) is pressed,
     * afterwards it checks if the user data that was entered by the user is valid, hence
     * included in the user_data.txt file. An Alert is popping up if the entered data is invalid
     * @throws IOException
     */
    public void login() throws IOException {
        username = tf_username.getText();
        password = pf_pwd.getText();

        if(!server.containsUserData(username, password)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An Error occurred");
            alert.setContentText("Username and/or password is incorrect");

            alert.showAndWait();
        }
        else{
            Stage stage = (Stage) tf_username.getScene().getWindow();
            FXMLLoader chatLoader = new FXMLLoader(MessengerApplication.class.getResource("chatScreen.fxml"));
            Parent root = chatLoader.load();
            stage.setTitle(username + "");
            stage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        }
    }

    /**
     * this method is called if btn_return is pressed in the GUI, afterwards it returns to the welcomeScreen
     * @throws IOException
     */
    public void goBack()throws IOException{
        FXMLLoader welcomeLoader = new FXMLLoader(MessengerApplication.class.getResource("welcomeScreen.fxml"));
        Stage welcome = (Stage) btn_return.getScene().getWindow();
        welcome.setResizable(false);
        welcome.setScene(new Scene(welcomeLoader.load()));
    }
}
