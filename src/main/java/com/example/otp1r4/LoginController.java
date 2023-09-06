package com.example.otp1r4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    String username;
    String password;

    public void clickLogin(ActionEvent actionEvent) {
        if(!usernameField.getText().isEmpty())
            username = usernameField.getText();

        if(!passwordField.getText().isEmpty())
            password = passwordField.getText();
    }

}