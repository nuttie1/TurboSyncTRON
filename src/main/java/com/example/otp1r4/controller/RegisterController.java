package com.example.otp1r4.controller;

import com.example.otp1r4.dao.UserDAO;
import com.example.otp1r4.model.UserData;
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
    UserDAO dao;
    UserData userData = UserData.getInstance();

    public RegisterController() {
        this.dao = new UserDAO();
    }

    public void backButtonOnAction(ActionEvent event) throws IOException {
        this.changeScene("login.fxml", (Node) event.getTarget());
    }

    public void submitButtonOnAction(ActionEvent event) throws Exception {
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
            dao.addUser(username, password, questionOne, questionTwo, questionThree, answerOne, answerTwo, answerThree);
            userData.setUsername(username);
            int userId = dao.getUserID(username);
            if (userId == -1)
                throw new Exception();
            userData.setUserID(userId);
            this.changeScene("mainView.fxml", submitButton);

            Stage stage = (Stage) submitButton.getScene().getWindow();
            showRegistrationSuccessMessage(stage);
        }
    }

    private void showRegistrationSuccessMessage(Stage ownerStage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Käyttäjä luotu");
        alert.setHeaderText(null);
        alert.setContentText("Käyttäjä luotu onnistuneesti!");

        alert.initOwner(ownerStage);
        alert.show();

        Duration duration = Duration.seconds(1.5);
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(duration);
        pause.setOnFinished(event -> alert.close());
        pause.play();
    }
}
