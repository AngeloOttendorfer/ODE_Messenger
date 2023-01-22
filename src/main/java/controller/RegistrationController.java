package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {
    /**
     * text-field for username registration
     */
    @FXML
    private TextField tf_username;

    /**
     * text-field for email registration
     */
    @FXML
    private TextField tf_email;

    /**
     * text-field for password registration
     */
    @FXML
    private PasswordField pf_pwd;

    /**
     * button to return to the welcomeScreen
     */
    @FXML
    private Button btn_return;

    /**
     * initializing the server to use the containsUserData method and the addUserData method
     */
    private Server server = new Server();

    /**
     * this method is called when the btn_signIn is pressed in the GUI, afterwards it sends the user data, including
     *        the username, email and password
     */
    public void registration() throws IOException{
        String username = "";
        String password = "";
        String email = "";

        if((tf_username.getText() != null) && (!tf_username.getText().trim().isEmpty())){
            username = tf_username.getText();
        }
        if((tf_email.getText() != null) && (!tf_email.getText().trim().isEmpty())){
            email = tf_email.getText();
        }

        if((pf_pwd.getText() != null) && (!pf_pwd.getText().trim().isEmpty())){
            password = pf_pwd.getText();
        }

        if(server.containsUserData(username, password)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An Error occurred");
            alert.setContentText("Username already exists");

            alert.showAndWait();
        }

        else{
            server.addUserData(username, password, email);

            FXMLLoader welcomeLoader = new FXMLLoader(MessengerApplication.class.getResource("welcomeScreen.fxml"));
            Stage welcome = (Stage) btn_return.getScene().getWindow();
            welcome.setResizable(false);
            welcome.setScene(new Scene(welcomeLoader.load()));
        }
    }

    /**
     * this method is called if btn_return is pressed in the GUI, afterwards it switches over to the welcomeScreen
     * @throws IOException
     */
    public void goBack() throws IOException{
        FXMLLoader welcomeLoader = new FXMLLoader(MessengerApplication.class.getResource("welcomeScreen.fxml"));
        Stage welcome = (Stage) btn_return.getScene().getWindow();
        welcome.setResizable(false);
        welcome.setScene(new Scene(welcomeLoader.load()));
    }
}
