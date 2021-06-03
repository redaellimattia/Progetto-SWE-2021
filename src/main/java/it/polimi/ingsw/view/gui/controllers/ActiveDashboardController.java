package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.DeckDashboard;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.network.client.ClientManager;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class ActiveDashboardController extends GuiController{
    @FXML //CHEST
    private Label xCoin,xServant,xShield,xRock;
    @FXML //LEADCARDS
    private ImageView leaderCard1,leaderCard2;
    @FXML //DEVCARDS
    private ImageView firstPositionLevel1,firstPositionLevel2,firstPositionLevel3,secondPositionLevel1,secondPositionLevel2,secondPositionLevel3,
            thirdPositionLevel1,thirdPositionLevel2,thirdPositionLevel3;
    @FXML //STORAGE
    private ImageView firstRowImage,secondRowImage1,secondRowImage2,thirdRowImage1,thirdRowImage2,thirdRowImage3;
    @FXML //UP LEFT BUTTONS
    private Button marketButton,shopButton,productionButton,endTurnButton,otherPlayersButton;
    @FXML //FAITHPATH
    private ImageView faithPath0,faithPath1,faithPath2,faithPath3,faithPath4,faithPath5,faithPath6,faithPath7,faithPath8,faithPath9,faithPath10,
            faithPath11,faithPath12,faithPath13,faithPath14,faithPath15,faithPath16,faithPath17,faithPath18,faithPath19,faithPath20,faithPath21,
            faithPath22,faithPath23,faithPath24;
    private PlayerDashboard playerDashboard;

    @FXML
    @Override
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        this.playerDashboard = getGuiManager().getClientManager().getThisClientDashboard();
        setFaithPath(playerDashboard.getPathPosition());
        setDevCards(playerDashboard.getDevCards());
    }

    private void setFaithPath(int position){
        switch (position){
            case 0: faithPath0.setVisible(true);
                break;
            case 1: faithPath1.setVisible(true);
                break;
            case 2: faithPath2.setVisible(true);
                break;
            case 3: faithPath3.setVisible(true);
                break;
            case 4: faithPath4.setVisible(true);
                break;
            case 5: faithPath5.setVisible(true);
                break;
            case 6: faithPath6.setVisible(true);
                break;
            case 7: faithPath7.setVisible(true);
                break;
            case 8: faithPath8.setVisible(true);
                break;
            case 9: faithPath9.setVisible(true);
                break;
            case 10: faithPath10.setVisible(true);
                break;
            case 11: faithPath11.setVisible(true);
                break;
            case 12: faithPath12.setVisible(true);
                break;
            case 13: faithPath13.setVisible(true);
                break;
            case 14: faithPath14.setVisible(true);
                break;
            case 15: faithPath15.setVisible(true);
                break;
            case 16: faithPath16.setVisible(true);
                break;
            case 17: faithPath17.setVisible(true);
                break;
            case 18: faithPath18.setVisible(true);
                break;
            case 19: faithPath19.setVisible(true);
                break;
            case 20: faithPath20.setVisible(true);
                break;
            case 21: faithPath21.setVisible(true);
                break;
            case 22: faithPath22.setVisible(true);
                break;
            case 23: faithPath23.setVisible(true);
                break;
            case 24: faithPath24.setVisible(true);
                break;
        }
    }

    private void setDevCards(DeckDashboard[] devCards){
        for(int i=0;i<3;i++){
            ArrayList<DevelopmentCard> deck = devCards[i].getDeck();
            if(deck.size()!=0){
                for(DevelopmentCard d:deck)
                    switch (d.getLevel()){
                        case 1: insertLevelOneCard(d.getId(),i);
                        break;
                        case 2: insertLevelTwoCard(d.getId(),i);
                            break;
                        case 3: insertLevelThreeCard(d.getId(),i);
                            break;
                    }
            }
        }
    }

    private void insertLevelOneCard(int ID,int i){
        insertCard(ID, i, firstPositionLevel1, firstPositionLevel2, firstPositionLevel3);
    }
    private void insertLevelTwoCard(int ID,int i){
        insertCard(ID, i, secondPositionLevel1, secondPositionLevel2, secondPositionLevel3);
    }
    private void insertLevelThreeCard(int ID,int i){
        insertCard(ID, i, thirdPositionLevel1, thirdPositionLevel2, thirdPositionLevel3);
    }
    private void insertCard(int ID, int i, ImageView level1, ImageView level2, ImageView level3) {
        switch (i){
            case 0: level1.setImage(new Image(this.getClass().getResourceAsStream("/img/cards/front/DevelopmentCards/"+ID+".png")));
                break;
            case 1: level2.setImage(new Image(this.getClass().getResourceAsStream("/img/cards/front/DevelopmentCards/"+ID+".png")));
                break;
            case 2: level3.setImage(new Image(this.getClass().getResourceAsStream("/img/cards/front/DevelopmentCards/"+ID+".png")));
                break;
        }
    }
}
