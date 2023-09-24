package com.example.otp1r4.controller;

import com.example.otp1r4.controller.Controller;
import com.example.otp1r4.dao.SignDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ForgotPasswordController implements Controller {

    @FXML
    private TextField usernameField, answerField1, answerField2, answerField3, newPasswordField;
    @FXML
    private Label usernameErrorLabel, questionLabel1, questionLabel2, questionLabel3, answerErrorLabel, newPasswordHeader, passwordErrorLabel, newPasswordLabel;
    @FXML
    private Button submitAnswersButton, changePasswordButton;

    SignDAO dao;

    public ForgotPasswordController() {
        this.dao = new SignDAO();
    }

    public void submitUsernameButton() {

        SignDAO dao = new SignDAO();
        usernameErrorLabel.setText("");

        if (usernameField.getText().isEmpty()) {
            usernameErrorLabel.setText("Syötä käyttäjätunnus!");
        } else if(dao.checkUsername(usernameField.getText())) {
            questionLabel1.setDisable(false);
            answerField1.setDisable(false);
            questionLabel2.setDisable(false);
            answerField2.setDisable(false);
            questionLabel3.setDisable(false);
            answerField3.setDisable(false);
            submitAnswersButton.setDisable(false);
            questionLabel1.setText(dao.getSecurityQuestion(usernameField.getText(), 1));
            questionLabel2.setText(dao.getSecurityQuestion(usernameField.getText(), 2));
            questionLabel3.setText(dao.getSecurityQuestion(usernameField.getText(), 3));
        } else {
            usernameErrorLabel.setText("Käyttäjää ei löytynyt!");
        }

    }

    public void submitAnswersButton() throws Exception {
        answerErrorLabel.setText("");

        if (answerField1.getText().isEmpty() || answerField2.getText().isEmpty() || answerField3.getText().isEmpty()) {
            answerErrorLabel.setText("Syötä vastaukset kysymyksiin!");
        } else if(dao.authenticateSecurityQuestions(usernameField.getText(),answerField1.getText(), answerField2.getText(), answerField3.getText())){
            newPasswordHeader.setDisable(false);
            newPasswordField.setDisable(false);
            newPasswordLabel.setDisable(false);
            changePasswordButton.setDisable(false);
        } else {
            answerErrorLabel.setText("Vastaukset eivät täsmää!");
        }

    }

    public void clickChangePassword() throws Exception {

        boolean isValid = true;
        String password = newPasswordField.getText();

        if(password.isEmpty()) {
            isValid = false;
            passwordErrorLabel.setText("Syötä salasana!");
        }
        if(isValid) {
            dao.changePassword(usernameField.getText(), newPasswordField.getText());
            System.out.println("Salasana vaihdettu!");
            this.changeScene("login.fxml", usernameField);
            Stage stage = (Stage) submitAnswersButton.getScene().getWindow();
            showChangePasswordSuccessMessage(stage);
        }

    }

    // TODO duplicate code; move this to MtoV interface
    private void showChangePasswordSuccessMessage(Stage ownerStage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Salasana vaihdettu");
        alert.setHeaderText(null);
        alert.setContentText("Salasana vaihdettu onnistuneesti!");

        alert.initOwner(ownerStage);
        alert.show();

        Duration duration = Duration.seconds(3);
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(duration);
        pause.setOnFinished(event -> alert.close());
        pause.play();
    }

    public void clickBack() throws IOException {
        this.changeScene("login.fxml", usernameField);
    }

}
