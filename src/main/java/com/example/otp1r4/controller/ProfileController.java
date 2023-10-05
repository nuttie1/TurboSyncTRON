package com.example.otp1r4.controller;

import com.example.otp1r4.dao.UserDAO;
import com.example.otp1r4.model.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController implements Controller{

    @FXML
    private TextField usernameField;
    @FXML
    private Label usernameErrorLabel, usernameSuccessLabel;
    @FXML
    private Button saveButton, clickEditButton;
    private String oldUsername;

    private UserData userData = UserData.getInstance();

    public void initialize() {
        usernameField.setText(userData.getUsername());
        oldUsername = userData.getUsername();
    }

    public void clickEdit() throws IOException {
        usernameField.setDisable(false);
        saveButton.setDisable(false);
        clickEditButton.setDisable(true);
    }

    public void clickSave() throws IOException {

        UserDAO dao = new UserDAO();
        String usernamePattern = "^[a-zA-Z0-9_]{3,20}$";
        boolean isValid = true;

        if(usernameField.getText().isEmpty()) {
            usernameField.setText("");
            usernameErrorLabel.setText("Syötä käyttäjätunnus!");
            isValid = false;
        } else if (!usernameField.getText().matches(usernamePattern)){
            usernameErrorLabel.setText("Syötä käyttäjätunnus\nhyväksytyssä muodossa!");
            isValid =false;
        } else if(dao.checkUsername(usernameField.getText())) {
            usernameErrorLabel.setText("Käyttäjätunnus varattu!");
            isValid = false;
        }

        if(isValid) {
            dao.changeUsername(usernameField.getText(), oldUsername);
            usernameField.setDisable(true);
            saveButton.setDisable(true);
            userData.setUsername(usernameField.getText());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            showSuccessMessage(stage, "Käyttäjätunnus vaihdettu", "Käyttäjätunnus vaihdettu onnistuneesti!", 3);
        }

    }

    public void clickBack() throws IOException {
        this.changeScene("mainView.fxml", usernameField);
    }

}
