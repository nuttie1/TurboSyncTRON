package tests;

import com.example.otp1r4.Main;
import com.example.otp1r4.controller.RegisterController;
import com.example.otp1r4.dao.JDBCConnection;
import com.example.otp1r4.dao.UserDAO;
import javafx.application.Application;
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
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

public class RegisterTest extends ApplicationTest {
    UserDAO userDAO = new UserDAO();

    @Override
    public void start(Stage stage) throws IOException {
        JDBCConnection jdbcConnection = new JDBCConnection();
        jdbcConnection.start();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/otp1r4/registerView.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    public void setUp() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
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
        clickOn("#usernameField").write("TestUser");
        clickOn("#passwordField").write("Password");
        clickOn("#questionOneField").write("Q1");
        clickOn("#answerOneField").write("A1");
        clickOn("#questionTwoField").write("Q2");
        clickOn("#answerTwoField").write("A2");
        clickOn("#questionThreeField").write("Q3");
        clickOn("#answerThreeField").write("A3");

        clickOn("#submitButton");

        assertTrue(userDAO.checkUser("TestUser"));
    }
}
