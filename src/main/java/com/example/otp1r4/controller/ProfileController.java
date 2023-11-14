package com.example.otp1r4.controller;

import com.example.otp1r4.dao.UserDAO;
import com.example.otp1r4.model.UserData;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ProfileController implements Controller {

    @FXML
    private TextField usernameField;
    @FXML
    private Label usernameErrorLabel;
    @FXML
    private Button saveButton, clickEditButton;
    @FXML
    private Hyperlink backLink;
    @FXML
    ComboBox languageBox;

    private String oldUsername;
    
    private UserData userData = UserData.getInstance();

    private UserDAO dao = new UserDAO();

    public void initialize() throws SQLException {
        usernameField.setText(userData.getUsername());
        oldUsername = userData.getUsername();
        languageBox.setValue(langDBtoGUI());
        languageBox.setItems(FXCollections.observableArrayList( "Suomi", "English", "中国人", "ދިވެހި"));
    }

    public void clickEdit() throws IOException {
        usernameField.setDisable(false);
        languageBox.setDisable(false);
        saveButton.setDisable(false);
        clickEditButton.setDisable(true);
    }

    public void clickSave() throws IOException {

        String usernamePattern = "^[a-zA-Z0-9_]{3,20}$";
        boolean isValid = true;

        if(usernameField.getText().isEmpty()) {
            usernameField.setText("");
            usernameErrorLabel.setText("Syötä käyttäjätunnus!");
            isValid = false;
        } else if (usernameField.getText().equals(oldUsername)) {
            isValid = true;
        } else if (!usernameField.getText().matches(usernamePattern)) {
            usernameErrorLabel.setText("Syötä käyttäjätunnus\nhyväksytyssä muodossa!");
            isValid =false;
        } else if(dao.checkUsername(usernameField.getText())) {
            usernameErrorLabel.setText("Käyttäjätunnus varattu!");
            isValid = false;
        }

        // Duplikaatti koodia :)
        String languageFromBox = languageBox.getValue().toString();
        String language = "";
        if(languageFromBox.equals("Suomi")){
            language = "Finnish";
        } else if (languageFromBox.equals("English")){
            language = "English";
        } else if (languageFromBox.equals("中国人")){
            language = "Chinese";
        } else if (languageFromBox.equals("ދިވެހި")){
            language = "Divehi";
        }

        if(isValid) {
            dao.changeUsername(usernameField.getText(), oldUsername);
            dao.changeLanguage(language, usernameField.getText());
            usernameField.setDisable(true);
            languageBox.setDisable(true);
            saveButton.setDisable(true);
            userData.setUsername(usernameField.getText());
            usernameErrorLabel.setText("");
            clickEditButton.setDisable(false);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            showSuccessMessage(stage, "Muutokset tallennettu", "Muutokset tallennettu onnistuneesti!", 3);
        }

    }

    public void clickBack() {
        Stage stage = (Stage) backLink.getScene().getWindow();
        stage.close();
    }

    public String langDBtoGUI() throws SQLException {
        if (dao.getLanguage(userData.getUsername()).equals("Finnish")) {
            return "Suomi";
        } else if (dao.getLanguage(userData.getUsername()).equals("English")) {
            return "English";
        } else if (dao.getLanguage(userData.getUsername()).equals("Chinese")) {
            return "中国人";
        } else if (dao.getLanguage(userData.getUsername()).equals("Divehi")) {
            return "ދިވެހި";
        }
        return "Tänne ei pitäs päästä!";
    }

    public void languageChanged() {
        // TODO: implement
    }

}
