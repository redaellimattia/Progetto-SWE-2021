package it.polimi.ingsw.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;

public class WaitingPageController extends GuiController{
    @FXML
    private ImageView gifPlayer;
    @FXML
    private Text textBox;

    @Override
    public void initialize() {
        super.initialize();
        gifPlayer.setImage(new Image(this.getClass().getResourceAsStream("/javaFX/hourglass.gif")));
    }

    @FXML
    @Override
    public void setTextForWaiting(String text){
        textBox.setText(text);
        textBox.setX(-250);
    }
}
