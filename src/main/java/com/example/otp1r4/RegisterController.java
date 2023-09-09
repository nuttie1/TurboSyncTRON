package com.example.otp1r4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class RegisterController {

    @FXML
    Button backButton;
    @FXML
    Button submitButton;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField questionOneField;
    @FXML
    TextField questionTwoField;
    @FXML
    TextField questionThreeField;
    @FXML
    TextField answerOneField;
    @FXML
    TextField answerTwoField;
    @FXML
    TextField answerThreeField;
    @FXML
    Label usernameErrorLabel;
    @FXML
    Label passwordErrorLabel;
    @FXML
    Label errorLabelQandA1;
    @FXML
    Label errorLabelQandA2;
    @FXML
    Label errorLabelQandA3;


    @FXML
    private void initialize() {

    }
    public void backButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void submitButtonOnAction(ActionEvent event) {

        boolean isValid = true;

        String username = usernameField.getText();
        String password = passwordField.getText();

        String questionOne = questionOneField.getText();
        String questionTwo = questionTwoField.getText();
        String questionThree = questionThreeField.getText();

        String answerOne = answerOneField.getText();
        String answerTwo = answerTwoField.getText();
        String answerThree = answerThreeField.getText();

        String usernamePattern = "^[a-zA-Z0-9_]{3,20}$";
        String inputPattern = "^[a-zA-Z0-9_ ]{1,100}$";


        if (username.isEmpty()) {
            isValid = false;
            usernameErrorLabel.setText("Syötä käyttäjätunnus!");
            usernameField.setText("");
        }   else if (!username.matches(usernamePattern)) {
            isValid = false;
            usernameErrorLabel.setText("Käyttäjätunnus ei hyväksytty");
            usernameField.setText("");
        }   else {
            usernameErrorLabel.setText("");
        }

        if (password.isEmpty()) {
            isValid = false;
            passwordErrorLabel.setText("Syötä salasana!");
            passwordField.setText("");
        }   else {
            passwordErrorLabel.setText("");
        }

        if (questionOne.isEmpty() && answerOne.isEmpty()) {
            isValid = false;
            errorLabelQandA1.setText("Syötä kysymys/vastaus!");
        }   else if (!questionOne.matches(inputPattern)) {
            isValid = false;
            errorLabelQandA1.setText("Kysymys/vastaus ei hyväksytty!");
        }   else {
            errorLabelQandA1.setText("");
        }

        if (questionTwo.isEmpty() && answerTwo.isEmpty()) {
            isValid = false;
            errorLabelQandA2.setText("Syötä kysymys/vastaus!");
        }   else if (!questionTwo.matches(inputPattern) || !answerTwo.matches(inputPattern)) {
            isValid = false;
            errorLabelQandA2.setText("Kysymys/vastaus ei hyväksytty!");
        }   else {
            errorLabelQandA2.setText("");
        }

        if (questionThree.isEmpty() && answerThree.isEmpty()) {
            isValid = false;
            errorLabelQandA3.setText("Syötä kysymys/vastaus!");
        }   else if (!questionThree.matches(inputPattern) || !answerThree.matches(inputPattern)) {
            isValid = false;
            errorLabelQandA3.setText("Kysymys/vastaus ei hyväksytty!");
        }   else {
            errorLabelQandA3.setText("");
        }

        if (isValid) {
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        }
    }

    /*
    public void onTextFieldClicked(KeyEvent event) {
        Node typed = (Node) event.getTarget();

        if (typed.equals(usernameField)) {
            usernameErrorLabel.setText("");
        }   else if (typed.equals(passwordField)) {
            passwordErrorLabel.setText("");
        }   else if (typed.equals(questionOneField)){
            questionErrorLabel1.setText("");
        }   else if (typed.equals(questionTwoField)){
            questionErrorLabel2.setText("");
        }   else if (typed.equals(questionThreeField)){
            questionErrorLabel3.setText("");
        }   else if (typed.equals(answerOneField)){
            answerErrorLabel1.setText("");
        }   else if (typed.equals(answerTwoField)){
            answerErrorLabel2.setText("");
        }   else if (typed.equals(answerThreeField)){
            answerErrorLabel3.setText("");
        }
    }
     */

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getQuestionOne() {
        return questionOneField.getText();
    }

    public String getQuestionTwo() {
        return questionTwoField.getText();
    }

    public String getQuestionThree() {
        return questionThreeField.getText();
    }

    public String getAnswerOne() {
        return answerOneField.getText();
    }

    public String getAnswerTwo() {
        return answerTwoField.getText();
    }

    public String getAnswerThree() {
        return answerThreeField.getText();
    }

}
