package com.example.otp1r4.controller;

import com.example.otp1r4.controller.Controller;
import com.example.otp1r4.dao.SignDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ForgotPasswordController implements Controller {

    @FXML
    private TextField usernameField, answerField1, answerField2, answerField3, newPasswordField;
    @FXML
    private Label usernameErrorLabel, questionLabel1, questionLabel2, questionLabel3, newPasswordHeader, passwordErrorLabel, newPasswordLabel;
    @FXML
    private Button submitAnswersButton, changePasswordButton;

    SignDAO dao;

    public ForgotPasswordController() {
        this.dao = new SignDAO();
    }

    public void submitUsernameButton() {
        usernameErrorLabel.setText("");

        if(dao.checkUsername(usernameField.getText())) {
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
        if(dao.authenticateSecurityQuestions(usernameField.getText(),answerField1.getText(), answerField2.getText(), answerField3.getText())){
            newPasswordHeader.setDisable(false);
            newPasswordField.setDisable(false);
            newPasswordLabel.setDisable(false);
            changePasswordButton.setDisable(false);
        }else {
            System.out.println("Jotkin tiedoista ovat väärin!");
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
        }
    }

    public void clickBack() throws IOException {
        this.changeScene("login.fxml", usernameField);
    }

}
