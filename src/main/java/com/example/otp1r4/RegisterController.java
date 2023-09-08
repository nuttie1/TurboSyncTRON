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

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty()) {
            usernameErrorLabel.setText("Syötä käyttäjätunnus!");
            usernameField.setText("");
        }   else if ("illegal".equals(inputValidation(username))) {
            usernameErrorLabel.setText("Käyttäjätunnus ei hyväksytty");
            usernameField.setText("");
        }

        if (password.isEmpty()) {
            passwordErrorLabel.setText("Syötä salasana!");
            passwordField.setText("");
        }

        /*
        Stage stage = (Stage) submitButton.getScene().getWindow();
        stage.close();

         */
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

    public static String inputValidation(String input) {

        String usernamePattern = "^[a-zA-Z0-9_]{3,20}$";

        if (input.isEmpty()) {
            return "empty";
        }   else if (input.matches(usernamePattern)) {
            return "illegal";
        }
        return "good";
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
