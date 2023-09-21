package com.example.otp1r4.controller;

import com.example.otp1r4.dao.SignDAO;
import com.example.otp1r4.model.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ProfileController {

    Utils u = new Utils();

    @FXML
    private TextField usernameField;
    @FXML
    private Label usernameErrorLabel, usernameSuccessLabel;
    @FXML
    private Button saveButton;
    private String oldUsername;

    private UserData userData = UserData.getInstance();

    public void initialize() {
        usernameField.setText(userData.getUsername());
        oldUsername = userData.getUsername();
    }

    public void clickEdit() throws IOException {
        usernameField.setDisable(false);
        saveButton.setDisable(false);
    }

    public void clickSave() throws IOException {

        SignDAO dao = new SignDAO();
        String usernamePattern = "^[a-zA-Z0-9_]{3,20}$";
        boolean isValid = true;

        if(usernameField.getText().isEmpty()) {
            usernameField.setText("");
            usernameErrorLabel.setText("Syötä käyttäjätunnus!");
            isValid = false;
        } else if (!usernameField.getText().matches(usernamePattern)){
            usernameErrorLabel.setText("Käyttäjätunnus ei kelpaa!");
            isValid =false;
        } else if(dao.checkUsername(usernameField.getText())) {
            usernameErrorLabel.setText("Käyttäjätunnus varattu!");
            isValid = false;
        }

        if(isValid) {
            dao.changeUsername(usernameField.getText(), oldUsername);
            usernameField.setDisable(true);
            saveButton.setDisable(true);
            usernameSuccessLabel.setText("Käyttäjätunnus vaihdettu!");
        }

    }

    public void clickBack() throws IOException {
        u.changeScene("mainView.fxml", usernameField);
    }

}
