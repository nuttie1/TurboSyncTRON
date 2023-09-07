package com.example.otp1r4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabelUsername;
    @FXML
    private Label errorLabelPassword;

    String username;
    String password;

    public void clickLogin(ActionEvent actionEvent) {

        if(usernameField.getText().isEmpty()){
            usernameField.setText("");
            errorLabelUsername.setText("Syötä käyttäjätunnus!");
        } else if (!usernameField.getText().matches("([A-Za-z0-9\\-\\_]+)")){
            errorLabelUsername.setText("Syötä käyttäjätunnus hyväksytyssä muodossa!");
        } else if (true){
            // Löytyykö kannasta
        }
        else {
            username = usernameField.getText();
        }

        if(passwordField.getText().isEmpty()){
            errorLabelPassword.setText("Syötä salasana!");
        } else if (true) {
            // Löytyykö kannasta
        }
        else {
            password = passwordField.getText();
        }
    }

}