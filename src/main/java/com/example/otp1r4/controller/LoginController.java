package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.dao.UserDAO;
import com.example.otp1r4.model.ObservableDevices;
import com.example.otp1r4.model.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    UserDAO dao;

    public LoginController() {
        this.dao = new UserDAO();
    }

    public void clickLogin(ActionEvent actionEvent) throws Exception {
        UserDAO dao = new UserDAO();

        username = usernameField.getText();
        password = passwordField.getText();

        errorLabelUsername.setText("");
        errorLabelPassword.setText("");

        boolean isValid = true;

        if(username.isEmpty()) {
            errorLabelUsername.setText("Syötä käyttäjätunnus!");
            isValid = false;
        }  else if (!username.matches("([A-Za-z0-9\\-\\_]+)")){
            usernameField.setText("");
            errorLabelUsername.setText("Syötä käyttäjätunnus\nhyväksytyssä muodossa!");
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
                int userId = dao.getUserID(username);
                if (userId == -1)
                    throw new Exception();
                userData.setUserID(userId);
                DeviceDAO deviceDAO = new DeviceDAO();
                ObservableDevices.getInstance().setObservableList(deviceDAO.getDevices(username));
                this.changeScene("mainView.fxml", usernameField);
            }else {
                errorLabelPassword.setText("Käyttäjätunnus tai salasana väärin!");
            }
        }
    }

    public void clickSignup() throws IOException {
        this.changeScene("registerView.fxml", usernameField);
    }

    public void clickForgotPassword() throws IOException{
        this.changeScene("forgotPasswordView.fxml", usernameField);
    }

}