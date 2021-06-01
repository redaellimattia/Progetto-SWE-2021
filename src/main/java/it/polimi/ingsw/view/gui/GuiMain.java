package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GuiMain extends Application {

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new Pane()));
        GuiManager guiManager = GuiManager.getInstance();
        guiManager.setStage(stage);
        guiManager.setCurrentScene(stage.getScene());
        guiManager.setLayout("/fxml/joinedLobby.fxml");
        stage.show();
        stage.setTitle("Masters Of Renaissance");
        stage.setFullScreen(true);

        stage.setOnCloseRequest((windowEvent) -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch();
    }

}

