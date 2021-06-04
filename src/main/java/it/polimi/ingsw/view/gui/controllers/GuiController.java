package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public abstract class GuiController {
    private GuiManager guiManager;

    public void setGuiManager(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    public void initialize(){}
    public void setLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> lobbies){}
    public void setPreGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources){}
    public void setTextForWaiting(String text){}
    public void setPlayer(PlayerDashboard player,boolean watchingPlayer){}

    protected void setImage(ImageView image, String path){
        image.setImage(new Image(this.getClass().getResourceAsStream(path)));
    }

    public void updateShop(){}
    public void updatePathPosition(String nickname){}
    public void updateLeaders(String nickname){}
    public void updateMarket(){}
    public void updateArrayDeposits(String nickname){}
    public void updateChest(String nickname){}
    public void updateDevCards(String nickname){}
    public void updateStorage(String nickname){}
    public void updateVaticanReports(){}
    //RICHIAMA TUTTO IL SET LAYOUT COSì SI POSSONO AGGIORNARE CORRETTAMENTE CARTE NON PIU CLICCABILI
    public void updateBufferProduction(String nickname){}
}
