package com.example.otp1r4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;

public class LoginController {

    Utils u = new Utils();

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

    public void clickLogin(ActionEvent actionEvent) throws IOException {

        errorLabelUsername.setText("");
        errorLabelPassword.setText("");

        boolean isValid = true;

        if(usernameField.getText().isEmpty()) {
            usernameField.setText("");
            errorLabelUsername.setText("Syötä käyttäjätunnus!");
            isValid = false;
        }  else if (!usernameField.getText().matches("([A-Za-z0-9\\-\\_]+)")){
            errorLabelUsername.setText("Syötä käyttäjätunnus hyväksytyssä muodossa!");
            isValid =false;
        }

        if (passwordField.getText().isEmpty()){
            errorLabelPassword.setText("Syötä salasana!");
            isValid = false;
        }

        if(isValid) {
            username = usernameField.getText();
            password = passwordField.getText();
            // Löytyykö kannasta
            u.changeScene("mainView.fxml", usernameField);
        }
    }

}