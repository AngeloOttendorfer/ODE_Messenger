module com.example.messengerchat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.messenger to javafx.fxml;
    exports com.example.messenger;
    exports controller;
    opens controller to javafx.fxml;
}