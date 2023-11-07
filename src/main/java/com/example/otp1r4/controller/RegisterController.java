package com.example.otp1r4.controller;

import com.example.otp1r4.dao.UserDAO;
import com.example.otp1r4.model.UserData;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController implements Controller {
    @FXML
    Button submitButton;
    @FXML
    TextField usernameField, questionOneField, questionTwoField, questionThreeField;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField answerOneField, answerTwoField, answerThreeField;
    @FXML
    Label usernameErrorLabel, passwordErrorLabel, errorLabelQandA1, errorLabelQandA2, errorLabelQandA3;
    @FXML
    ComboBox languageBox;

    UserDAO dao;
    UserData userData = UserData.getInstance();

    public RegisterController() {
        this.dao = new UserDAO();
    }

    public void initialize() {
        languageBox.setItems(FXCollections.observableArrayList( "Suomi", "English", "中国人"));
    }

    public void backButtonOnAction(ActionEvent event) throws IOException {
        this.changeScene("login.fxml", (Node) event.getTarget());
    }

    public void submitButtonOnAction(ActionEvent event) throws Exception {
        boolean isValid = true;

        String username = usernameField.getText();
        String password = passwordField.getText();

        String languageFromBox = languageBox.getValue().toString();
        String language = "Finnish";
        if(languageFromBox.equals("Suomi")){
            language = "Finnish";
        } else if (languageFromBox.equals("English")){
            language = "English";
        } else if (languageFromBox.equals("中国人")){
            language = "Chinese";
        }

        String questionOne = questionOneField.getText();
        String questionTwo = questionTwoField.getText();
        String questionThree = questionThreeField.getText();

        String answerOne = answerOneField.getText();
        String answerTwo = answerTwoField.getText();
        String answerThree = answerThreeField.getText();

        String usernamePattern = "^[a-zA-Z0-9_]{3,20}$";
        String inputPattern = "^[a-zA-Z0-9_äöåÄÖÅ ]{1,100}$";

        usernameErrorLabel.setText("");
        passwordErrorLabel.setText("");
        errorLabelQandA1.setText("");
        errorLabelQandA2.setText("");
        errorLabelQandA3.setText("");

        boolean userTaken = false;

        if (dao.checkUsername(username)) {
            usernameErrorLabel.setText("Käyttäjätunnus varattu!");
            usernameField.setText("");
            isValid = false;
            userTaken = true;
        }

        if (username.isEmpty()) {
            isValid = false;
            usernameErrorLabel.setText("Syötä käyttäjätunnus!");
        }   else if (!username.matches(usernamePattern)) {
            isValid = false;
            usernameErrorLabel.setText("Syötä käyttäjätunnus\nhyväksytyssä muodossa!");
            usernameField.setText("");
        }   else if (!userTaken){
            usernameErrorLabel.setText("");
        }

        if (password.isEmpty()) {
            isValid = false;
            passwordErrorLabel.setText("Syötä salasana!");
        }   else {
            passwordErrorLabel.setText("");
        }

        if (questionOne.isEmpty() || answerOne.isEmpty()) {
            isValid = false;
            errorLabelQandA1.setText("Syötä kysymys/vastaus!");
        }   else if (!questionOne.matches(inputPattern) || !answerOne.matches(inputPattern)) {
            isValid = false;
            errorLabelQandA1.setText("Syötä kysymys/vastaus hyväksytyssä muodossa!");
        }   else {
            errorLabelQandA1.setText("");
        }

        if (questionTwo.isEmpty() || answerTwo.isEmpty()) {
            isValid = false;
            errorLabelQandA2.setText("Syötä kysymys/vastaus!");
        }   else if (!questionTwo.matches(inputPattern) || !answerTwo.matches(inputPattern)) {
            isValid = false;
            errorLabelQandA2.setText("Kysymys/vastaus ei hyväksytty!");
        }   else {
            errorLabelQandA2.setText("");
        }

        if (questionThree.isEmpty() || answerThree.isEmpty()) {
            isValid = false;
            errorLabelQandA3.setText("Syötä kysymys/vastaus!");
        }   else if (!questionThree.matches(inputPattern) || !answerThree.matches(inputPattern)) {
            isValid = false;
            errorLabelQandA3.setText("Kysymys/vastaus ei hyväksytty!");
        }   else {
            errorLabelQandA3.setText("");
        }
        if (isValid) {
            dao.addUser(username, language, password, questionOne, questionTwo, questionThree, answerOne, answerTwo, answerThree);
            userData.setUsername(username);
            int userId = dao.getUserID(username);
            if (userId == -1)
                throw new Exception();
            userData.setUserID(userId);
            this.changeScene("mainView.fxml", submitButton);

            Stage stage = (Stage) submitButton.getScene().getWindow();
            showSuccessMessage(stage, "Käyttäjä luotu", "Käyttäjä luotu onnistuneesti!", 3);
        }
    }

    public void languageChanged() {
        // TODO: implement
    }

}
