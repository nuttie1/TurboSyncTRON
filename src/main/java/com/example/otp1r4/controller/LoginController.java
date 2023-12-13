package com.example.otp1r4.controller;

import com.example.otp1r4.dao.DeviceDAO;
import com.example.otp1r4.dao.UserDAO;
import com.example.otp1r4.model.Language;
import com.example.otp1r4.model.ObservableDevices;
import com.example.otp1r4.model.UserData;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
    private ComboBox<String> languageBox;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button loginButton;

    String username;
    String password;
    String userLanguage = "";
    UserDAO dao;
    private ResourceBundle bundle;

    public LoginController() {
        this.dao = new UserDAO();
    }

    public void initialize() {
           List<String> languages = new ArrayList<>();
            languages.add("Suomi");
            languages.add("English");
            languages.add("中国人");
            languageBox.setItems(FXCollections.observableArrayList(languages));
            languageBox.getSelectionModel().selectFirst();
            languageBox.setOnAction(event -> updateLocale(languageBox.getValue()));
            updateLocale(languageBox.getValue());
    }

    public void clickLogin(ActionEvent actionEvent) throws Exception {
        UserDAO dao = new UserDAO();

        username = usernameField.getText();
        password = passwordField.getText();

        errorLabelUsername.setText("");
        errorLabelPassword.setText("");

        boolean isValid = true;

        if(username.isEmpty()) {
            errorLabelUsername.setText(bundle.getString("errorEmptyUsername"));
            isValid = false;
        }  else if (!username.matches("([A-Za-z0-9\\-\\_]+)")){
            usernameField.setText("");
            errorLabelUsername.setText(bundle.getString("errorIllegalUsername"));
            isValid =false;
        }

        if (password.isEmpty()){
            errorLabelPassword.setText(bundle.getString("errorEmptyPassword"));
            isValid = false;
        }

        if(isValid) {
            if(dao.authenticate(username,password)){
                dao.changeLanguage(userLanguage, username);
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
                errorLabelPassword.setText(bundle.getString("errorWrongPassword"));
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
                if (userLanguage != "English" && userLanguage != "") {
                    swapNodes(usernameField, usernameLabel);
                    swapNodes(passwordField, passwordLabel);
                }
                userLanguage = "Finnish";
                break;
            case "English":
                newLocale = Locale.US;
                if (userLanguage != "Finnish" && userLanguage != "") {
                    swapNodes(usernameField, usernameLabel);
                    swapNodes(passwordField, passwordLabel);
                }
                userLanguage = "English";
                break;
            case "中国人":
                newLocale = Locale.SIMPLIFIED_CHINESE;
                userLanguage = "Chinese";
                swapNodes2(usernameLabel, usernameField);
                swapNodes2(passwordLabel, passwordField);
                break;
            case "":
                newLocale = new Locale("mv");
                break;
        }
        bundle = ResourceBundle.getBundle("TextResources", newLocale);
        updateUI();
    }

    private void updateUI() {
        errorLabelUsername.setText("");
        errorLabelPassword.setText("");

        usernameLabel.setText(bundle.getString("usernameLabel"));
        passwordLabel.setText(bundle.getString("passwordLabel"));
        signUpLink.setText(bundle.getString("createUser"));
        forgotPasswordLink.setText(bundle.getString("forgotPassword"));
        loginButton.setText(bundle.getString("loginButton"));
    }

    private void swapNodes(Node nodeLeft, Node nodeRight) {
        double x1 = nodeLeft.getLayoutX();
        double x2 = nodeRight.getBoundsInParent().getMaxX() - nodeLeft.getBoundsInParent().getWidth();

        nodeLeft.setLayoutX(378);
        nodeRight.setLayoutX(269);
    }

    private void swapNodes2(Node nodeLeft, Node nodeRight) {
        double x1 = nodeLeft.getLayoutX();
        double x2 = nodeRight.getBoundsInParent().getMaxX() - nodeLeft.getBoundsInParent().getWidth()+50;

        nodeLeft.setLayoutX(x2);
        nodeRight.setLayoutX(x1);
    }

    private String getUtfText(String key, Locale locale) {
        String fileName = "TextResources_" + locale.toString() + ".properties";

        Properties properties = new Properties();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName);
             InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}