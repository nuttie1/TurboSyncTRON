package tests;

import com.example.otp1r4.controller.RegisterController;
import com.example.otp1r4.dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.tools.Platform;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterTest {
    private RegisterController controller;

    UserDAO userDAO = new UserDAO();

    @BeforeEach
    public void setUp() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("src/main/java/com/example/otp1r4/controller/registerView.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        controller = loader.getController();
        stage.show();
    }

    @AfterAll
    public static void removeTestUser() throws SQLException {
        UserDAO userDAO = new UserDAO();
        if (userDAO.checkUser("TestUser")) {
            userDAO.removeUser("TestUser");
        }
    }

    @Test
    public void newUserTest() {
        controller.usernameField.setText("TestUser");
        controller.passwordField.setText("Password");
        controller.questionOneField.setText("Q1");
        controller.answerOneField.setText("A1");
        controller.questionTwoField.setText("Q2");
        controller.answerTwoField.setText("A2");
        controller.questionThreeField.setText("Q3");
        controller.answerThreeField.setText("A3");

        try {
            controller.submitButtonOnAction(new ActionEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(userDAO.checkUser("TestUser"));
    }
}
