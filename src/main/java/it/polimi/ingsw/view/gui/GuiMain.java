package it.polimi.ingsw.view.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GuiMain extends Application {

    /**
     * starting a new Stage loading the SplashScreen who will perform the connection to the server.
     * setting parameters for the window such as not resizable, title, icon.
     * @param stage gui window
     */
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new Pane()));
        GuiManager guiManager = GuiManager.getInstance();
        guiManager.setStage(stage);
        guiManager.setCurrentScene(stage.getScene());
        guiManager.setLayout("splashScreen.fxml");
        stage.setTitle("Masters Of Renaissance");
        stage.setResizable(false);
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/img/javaFX/icon.png")));
        stage.show();

        stage.setOnCloseRequest((windowEvent) -> {
            Platform.exit();
            System.exit(0);
        });
    }
}

