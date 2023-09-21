package com.example.otp1r4.controller;

import com.example.otp1r4.dao.SignDAO;
import com.example.otp1r4.model.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController implements Controller {
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabelUsername;
    @FXML
    private Label errorLabelPassword;
    @FXML
    private Hyperlink signUpLink;

    String username;
    String password;
    SignDAO dao;

    public LoginController(SignDAO dao) {
        this.dao = dao;
    }

    public void clickLogin(ActionEvent actionEvent) throws Exception {
        username = usernameField.getText();
        password = passwordField.getText();

        boolean isValid = true;

        if(username.isEmpty()) {
            usernameField.setText("");
            errorLabelUsername.setText("Syötä käyttäjätunnus!");
            isValid = false;
        }  else if (!username.matches("([A-Za-z0-9\\-\\_]+)")){
            errorLabelUsername.setText("Syötä käyttäjätunnus hyväksytyssä muodossa!");
            isValid =false;
        }

        if (password.isEmpty()){
            errorLabelPassword.setText("Syötä salasana!");
            isValid = false;
        }

        if(isValid) {
            if(dao.authenticate(username,password)){
                UserData userData = UserData.getInstance();
                userData.setUsername(username);
                this.changeScene("mainView.fxml", usernameField);
            }else {
                System.out.println("Käyttäjää ei löytynyt!");
            }
        }
    }

    public void clickSignup() throws IOException {
        Stage stage = (Stage) signUpLink.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("registerView.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public void clickForgotPassword() throws IOException{
        this.changeScene("forgotPasswordView.fxml", usernameField);
    }
}
