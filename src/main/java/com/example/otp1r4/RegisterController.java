package com.example.otp1r4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.Objects;

public class RegisterController {
    boolean validData = true;
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
    Label questionErrorLabel1;
    @FXML
    Label questionErrorLabel2;
    @FXML
    Label questionErrorLabel3;
    @FXML
    Label answerErrorLabel1;
    @FXML
    Label answerErrorLabel2;
    @FXML
    Label answerErrorLabel3;


    @FXML
    private void initialize() {

    }
    public void backButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();

        //AVAA LOGIN
    }

    public void submitButtonOnAction(ActionEvent event) {
        if (inputValidation(usernameField.getText()) && !(Objects.equals(passwordField.getText(), ""))){
            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        }   else {
            usernameErrorLabel.setText("Username not valid");
            passwordErrorLabel.setText("Password not valid");
            usernameField.setText("");
            passwordField.setText("");
        }
    }

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

    public static boolean inputValidation(String username) {
        if (username == null) {
            return false;
        }
        String usernamePattern = "^[a-zA-Z0-9_]{3,20}$";

        return username.matches(usernamePattern);
    }

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
