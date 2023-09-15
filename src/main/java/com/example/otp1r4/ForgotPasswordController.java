package com.example.otp1r4;

import com.example.otp1r4.dao.SignDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ForgotPasswordController {

    Utils u = new Utils();

    @FXML
    private TextField usernameField, questionField1, questionField2, questionField3;
    @FXML
    private TextField answerField1, answerField2, answerField3;
    @FXML
    private Label newPasswordHeader;
    @FXML
    private TextField newPasswordField;
    @FXML
    private Label newPasswordLabel;
    @FXML
    private Button changePasswordButton;


    public void submitButton() throws Exception {
        SignDAO dao = new SignDAO();

        if(dao.authenticateSecurityQuestions(usernameField.getText(),answerField1.getText(), answerField2.getText(), answerField3.getText())){
            newPasswordHeader.setDisable(false);
            newPasswordField.setDisable(false);
            newPasswordLabel.setDisable(false);
            changePasswordButton.setDisable(false);
        }else {
            System.out.println("Jotkin tiedoista ovat väärin!");
        }

    }

    public void clickBack() throws IOException {
        u.changeScene("login.fxml", usernameField);
    }

}
