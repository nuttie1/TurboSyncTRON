package com.example.otp1r4.controller;

import com.example.otp1r4.Main;
import com.example.otp1r4.model.LocaleManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** {@code @Brief} Interface for controllers - model to view.
 *  Methods that are needed for in model to view order.
 *
 *  See VtoM Interface for view to model applications.
 */
public interface Controller {
    /**
     * @param nextView fxml file name ex. mainView.fxml
     * @param currentNode Scene node 'object' ex. TextField usernameField
     * @throws IOException
     */
    default void changeScene(String nextView, Node currentNode) throws IOException {
        //Parent root = FXMLLoader.load(Main.class.getResource(nextView));
        Parent root = new FXMLLoader(Main.class.getResource(nextView))
                .load(Main.class.getResource(nextView), LocaleManager.getInstance().getBundle());
        Stage window = (Stage) currentNode.getScene().getWindow();
        window.setScene(new Scene(root));
        window.centerOnScreen();
    }
    /**
     * @param nextView fxml file name ex. mainView.fxml
     * @param currentNode Scene node 'object' ex. TextField usernameField
     * @throws IOException
     */
    default void addSceneOnTop(String nextView, Node currentNode) throws IOException {
        //Parent root = FXMLLoader.load(Main.class.getResource(nextView));
        Parent root = new FXMLLoader(Main.class.getResource(nextView))
                .load(Main.class.getResource(nextView), LocaleManager.getInstance().getBundle());
        Stage window = new Stage();
        window.initOwner(currentNode.getScene().getWindow());

        window.setScene(new Scene(root));
        window.setTitle(nextView);
        window.show();
    }

    default void showSuccessMessage(Stage ownerStage, String title, String contentText, int seconds) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        alert.initOwner(ownerStage);
        alert.show();

        Duration duration = Duration.seconds(seconds);
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(duration);
        pause.setOnFinished(event -> alert.close());
        pause.play();
    }

}
