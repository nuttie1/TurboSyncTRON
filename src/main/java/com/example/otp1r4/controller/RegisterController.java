package com.example.otp1r4.controller;

import com.example.otp1r4.dao.SignDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

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
    SignDAO dao;
    public RegisterController() {
        dao = new SignDAO();
    }


    public void backButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void submitButtonOnAction(ActionEvent event) throws IOException {
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

        boolean userTaken = false;

        if (dao.checkUsername(username)) {
            usernameErrorLabel.setText("Käyttäjä tunnus varattu!");
            usernameField.setText("");
            isValid = false;
            userTaken = true;
        }

        if (username.isEmpty()) {
            isValid = false;
            usernameErrorLabel.setText("Syötä käyttäjätunnus!");
            usernameField.setText("");
        } else if (!username.matches(usernamePattern)) {
            isValid = false;
            usernameErrorLabel.setText("Käyttäjätunnus ei hyväksytty");
            usernameField.setText("");
        } else if (!userTaken) {
            usernameErrorLabel.setText("");
        }

        if (password.isEmpty()) {
            isValid = false;
            passwordErrorLabel.setText("Syötä salasana!");
            passwordField.setText("");
        } else {
            passwordErrorLabel.setText("");
        }

        if (questionOne.isEmpty() && answerOne.isEmpty()) {
            isValid = false;
            errorLabelQandA1.setText("Syötä kysymys/vastaus!");
        } else if (!questionOne.matches(inputPattern) || !answerOne.matches(inputPattern)) {
            isValid = false;
            errorLabelQandA1.setText("Kysymys/vastaus ei hyväksytty!");
        } else {
            errorLabelQandA1.setText("");
        }

        if (questionTwo.isEmpty() && answerTwo.isEmpty()) {
            isValid = false;
            errorLabelQandA2.setText("Syötä kysymys/vastaus!");
        } else if (!questionTwo.matches(inputPattern) || !answerTwo.matches(inputPattern)) {
            isValid = false;
            errorLabelQandA2.setText("Kysymys/vastaus ei hyväksytty!");
        } else {
            errorLabelQandA2.setText("");
        }

        if (questionThree.isEmpty() && answerThree.isEmpty()) {
            isValid = false;
            errorLabelQandA3.setText("Syötä kysymys/vastaus!");
        } else if (!questionThree.matches(inputPattern) || !answerThree.matches(inputPattern)) {
            isValid = false;
            errorLabelQandA3.setText("Kysymys/vastaus ei hyväksytty!");
        } else {
            errorLabelQandA3.setText("");
        }
        if (isValid) {
            dao.addUser(username, password, questionOne, questionTwo, questionThree, answerOne, answerTwo, answerThree);

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