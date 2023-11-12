package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.dao.UserDAO;
import com.example.otp1r4.model.Language;
import com.example.otp1r4.model.ObservableDevices;
import com.example.otp1r4.model.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
    @FXML
    private Hyperlink forgotPasswordLink;
    @FXML
    private ChoiceBox<String> languageChoiceBox;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button loginButton;

    String username;
    String password;
    UserDAO dao;

    private ResourceBundle bundle;

    String[] languages = {"Suomi", "English", "中国人", "ދިވެހި..."};

    public LoginController() {
        this.dao = new UserDAO();
    }

    public void initialize() {
        languageChoiceBox.getItems().addAll(languages);
        languageChoiceBox.setValue("Suomi");

        Language.setLocale(new Locale("fi"));
        bundle = ResourceBundle.getBundle("TextResources");

        languageChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateLocale(newValue);
            }
        });
    }

    public void clickLogin(ActionEvent actionEvent) throws Exception {
        UserDAO dao = new UserDAO();

        username = usernameField.getText();
        password = passwordField.getText();

        errorLabelUsername.setText("");
        errorLabelPassword.setText("");

        boolean isValid = true;


        if(username.isEmpty()) {
            errorLabelUsername.setText(Language.getString("errorEmptyUsername"));
            isValid = false;
        }  else if (!username.matches("([A-Za-z0-9\\-\\_]+)")){
            usernameField.setText("");
            errorLabelUsername.setText(Language.getString("errorIllegalUsername"));
            isValid =false;
        }

        if (password.isEmpty()){
            errorLabelPassword.setText(Language.getString("errorEmptyPassword"));
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
                errorLabelPassword.setText(Language.getString("errorWrongPassword"));
            }
        }
    }

    public void clickSignup() throws IOException {
        this.changeScene("registerView.fxml", usernameField);
    }

    public void clickForgotPassword() throws IOException{
        this.changeScene("forgotPasswordView.fxml", usernameField);
    }

    private void updateLocale(String language) {
        Locale newLocale = null;

        switch (language) {
            case "Suomi":
                newLocale = new Locale("fi");
                break;
            case "English":
                newLocale = Locale.US;
                break;
            case "中国人":
                newLocale = Locale.SIMPLIFIED_CHINESE;
                break;
            case "":
                newLocale = new Locale("mv");
                break;
        }

        Language.setLocale(newLocale);
        updateUI();
    }

    private void updateUI() {
        errorLabelUsername.setText("");
        errorLabelPassword.setText("");

        usernameLabel.setText(Language.getString("usernameLabel"));
        passwordLabel.setText(Language.getString("passwordLabel"));
        signUpLink.setText(Language.getString("createUser"));
        forgotPasswordLink.setText(Language.getString("forgotPassword"));
        loginButton.setText(Language.getString("loginButton"));
    }
}