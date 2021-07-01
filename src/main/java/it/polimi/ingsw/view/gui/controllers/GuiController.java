package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
import it.polimi.ingsw.network.client.PlayerPoints;
import it.polimi.ingsw.network.messages.serverMessages.ReturnLobbiesMessage;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Gui Controller abstract class
 */
public abstract class GuiController {
    private GuiManager guiManager;

    public void setGuiManager(GuiManager guiManager) {
        this.guiManager = guiManager;
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }

    //INITIAL SET
    public void initialize(){}
    public void setLobbies(ArrayList<ReturnLobbiesMessage.availableGameLobbies> lobbies){}
    public void setPreGameChoice(ArrayList<LeaderCard> leaders, int numberOfResources){}
    public void setTextForWaiting(String text){}
    public void setPlayer(PlayerDashboard player,boolean watchingPlayer){}
    public void setBuyCard(DevelopmentCard card){}
    public void setDevCardProduction(DevelopmentCard card){}
    public void setLeaderCardProduction(LeaderCard card, Resource res){}
    public void setBasicProduction(Resource res){}
    public void setFinalStageBuy(DevelopmentCard card,ResourceCount storageCount, ResourceCount chestCount){}
    public void setProductionOnGoing(){}
    public void setEndGame(ArrayList<PlayerPoints> scoreboard){}
    public void setEndGame(boolean lorenzoWin, int playerPoints){}

    //UPDATES
    public void updateShop(){}
    public void updatePathPosition(String nickname){}
    public void updateLeaders(String nickname){}
    public void updateMarket(){}
    public void updateArrayDeposits(String nickname){}
    public void updateChest(String nickname){}
    public void updateDevCards(String nickname){}
    public void updateStorage(String nickname){}
    public void updateVaticanReports(){}
    public void updateBufferProduction(String nickname){}
    public void updateLogger(ArrayList<String> log){}


    /**
     * Sets the image on the passed ImageView
     *
     * @param image ImageView object
     * @param path path of the image
     */
    protected void setImage(ImageView image, String path){
        if(path == null)
            image.setImage(null);
        else
            image.setImage(new Image(this.getClass().getResourceAsStream(path)));
    }

    /**
     * Sets the storage
     *
     * @param storage Storage of the player
     * @param firstRowImage ImageView of the first row
     * @param secondRowImage1 ImageView of the first resource of the second row
     * @param secondRowImage2 ImageView of the second resource of the second row
     * @param thirdRowImage1 ImageView of the first resource of the third row
     * @param thirdRowImage2 ImageView of the second resource of the third row
     * @param thirdRowImage3 ImageView of the third resource of the third row
     */
    protected void setStorage(Storage storage,ImageView firstRowImage,ImageView secondRowImage1,ImageView secondRowImage2,ImageView thirdRowImage1,ImageView thirdRowImage2,ImageView thirdRowImage3){
        ArrayList<CounterTop> shelves = storage.getShelvesArray();
        if(shelves.size()!=0){
            for(int i=0;i<shelves.size();i++)
                setStorageRow(shelves.get(i), i + 1,firstRowImage,secondRowImage1,secondRowImage2,thirdRowImage1,thirdRowImage2,thirdRowImage3);
        }
    }

    /**
     * Sets a storageRow
     *
     * @param row CounterTop of the row
     * @param i Index of the rows
     * @param firstRowImage ImageView of the first row
     * @param secondRowImage1 ImageView of the first resource of the second row
     * @param secondRowImage2 ImageView of the second resource of the second row
     * @param thirdRowImage1 ImageView of the first resource of the third row
     * @param thirdRowImage2 ImageView of the second resource of the third row
     * @param thirdRowImage3 ImageView of the third resource of the third row
     */
    private void setStorageRow(CounterTop row,int i,ImageView firstRowImage,ImageView secondRowImage1,ImageView secondRowImage2,ImageView thirdRowImage1,ImageView thirdRowImage2,ImageView thirdRowImage3){
        switch(i){
            case 1:
                if(row.getContent()==0)
                    setImage(firstRowImage,null);
                else
                    setImage(firstRowImage,getCounterTopImage(row.getResourceType()));
                break;
            case 2: String path = getCounterTopImage(row.getResourceType());
                switch(row.getContent()){
                    case 0: setImage(secondRowImage1,null);
                        setImage(secondRowImage2,null);
                        break;
                    case 1: setImage(secondRowImage1,path);
                        setImage(secondRowImage2,null);
                        break;
                    case 2: setImage(secondRowImage1,path);
                        setImage(secondRowImage2,path);
                        break;
                }
                break;
            case 3: path = getCounterTopImage(row.getResourceType());
                switch(row.getContent()){
                    case 0: setImage(thirdRowImage1,null);
                        setImage(thirdRowImage2,null);
                        setImage(thirdRowImage3,null);
                        break;
                    case 1: setImage(thirdRowImage1,path);
                        setImage(thirdRowImage2,null);
                        setImage(thirdRowImage3,null);
                        break;
                    case 2: setImage(thirdRowImage1,path);
                        setImage(thirdRowImage2,path);
                        setImage(thirdRowImage3,null);
                        break;
                    case 3: setImage(thirdRowImage1,path);
                        setImage(thirdRowImage2,path);
                        setImage(thirdRowImage3,path);
                        break;
                }
                break;
        }
    }

    /**
     * Gets the image path based on the type of the resource of the row
     *
     * @param res Resource of the CounterTop
     * @return path of the image of the resource
     */
    protected String getCounterTopImage(Resource res){
        switch(res){
            case COIN:  return "/img/punchboard/coin2.png";
            case ROCK:  return "/img/punchboard/stone2.png";
            case SHIELD:  return "/img/punchboard/shield2.png";
            case SERVANT:  return "/img/punchboard/servant2.png";
            default: return null;
        }
    }

    /**
     * Setting the images of development cards deck for a player (used both in clientDashboard and endBuyCard)
     * @param devCards player's devCards
     * @param firstDeck1 first deck level 1
     * @param secondDeck1 second deck level 1
     * @param thirdDeck1 third deck level 1
     * @param firstDeck2 first deck level 2
     * @param secondDeck2 second deck level 2
     * @param thirdDeck2 third deck level 2
     * @param firstDeck3 first deck level 3
     * @param secondDeck3 second deck level 3
     * @param thirdDeck3 third deck level 3
     */
    protected void setDevCards(DeckDashboard[] devCards,ImageView firstDeck1, ImageView secondDeck1, ImageView thirdDeck1,
                                ImageView firstDeck2, ImageView secondDeck2, ImageView thirdDeck2,
                                ImageView firstDeck3, ImageView secondDeck3, ImageView thirdDeck3){
        for(int i=0;i<3;i++){
            ArrayList<DevelopmentCard> deck = devCards[i].getDeck();
            if(deck.size()!=0){
                for(DevelopmentCard d:deck)
                    switch (d.getLevel()){
                        case 1: insertCard(d.getId(),i,firstDeck1,secondDeck1,thirdDeck1);
                            break;
                        case 2: insertCard(d.getId(),i,firstDeck2,secondDeck2,thirdDeck2);
                            break;
                        case 3: insertCard(d.getId(),i,firstDeck3,secondDeck3,thirdDeck3);
                            break;
                    }
            }
        }
    }

    /**
     * Supporting function used by setDevCards
     * @param ID id of the card
     * @param i position of the card in the specific deck
     * @param firstPosition deck level 1
     * @param secondPosition deck level 2
     * @param thirdPosition deck level 3
     */
    protected void insertCard(int ID, int i, ImageView firstPosition, ImageView secondPosition, ImageView thirdPosition) {
        switch (i){
            case 0: setImage(firstPosition,"/img/cards/front/DevelopmentCards/"+ID+".png");
                break;
            case 1: setImage(secondPosition,"/img/cards/front/DevelopmentCards/"+ID+".png");
                break;
            case 2: setImage(thirdPosition,"/img/cards/front/DevelopmentCards/"+ID+".png");
                break;
        }
    }

    /**
     * Sets the chest
     *
     * @param chest Chest of the Player
     * @param xCoin Label for coin
     * @param xShield Label for shield
     * @param xRock Label for rock
     * @param xServant Label for servant
     */
    protected void setChest(ResourceCount chest, Label xCoin, Label xShield,Label xRock, Label xServant){
        if(chest.getCoins()!=0)
            xCoin.setText("x"+chest.getCoins());
        else
            xCoin.setText("x0");
        if(chest.getShields()!=0)
            xShield.setText("x"+chest.getShields());
        else
            xShield.setText("x0");
        if(chest.getRocks()!=0)
            xRock.setText("x"+chest.getRocks());
        else
            xRock.setText("x0");
        if(chest.getServants()!=0)
            xServant.setText("x"+chest.getServants());
        else
            xServant.setText("x0");
    }

    /**
     * Adds resources when clicked
     *
     * @param res Clicked Resource
     * @param xLabel Label cliked
     * @param rc ResourceCount that will be updated
     * @param count Amount of resources that are needed
     * @param coin ImageView of the coin
     * @param shield ImageView of the shield
     * @param servant ImageView of the servant
     * @param rock ImageView of the rock
     */
    @FXML
    protected void addResource(Resource res, Label xLabel,ResourceCount rc,int count,ImageView coin,ImageView shield,ImageView servant,ImageView rock){
        if(count>ResourceCount.resCountToInt(rc)){
            res.add(rc,1);
            if(!xLabel.isVisible())
                xLabel.setVisible(true);
            if(xLabel.isDisable())
                xLabel.setDisable(false);
            xLabel.setText("x"+res.get(rc));
            if(count==ResourceCount.resCountToInt(rc))
                disableResourcesClick(coin,shield,servant,rock);
        }
    }

    /**
     * Removes resources when clicked
     *
     * @param res Clicked Resource
     * @param xLabel Label cliked
     * @param rc ResourceCount that will be updated
     * @param coin ImageView of the coin
     * @param shield ImageView of the shield
     * @param servant ImageView of the servant
     * @param rock ImageView of the rock
     */
    @FXML
    protected void removeResource(Resource res, Label xLabel,ResourceCount rc,ImageView coin,ImageView shield,ImageView servant,ImageView rock){
        res.remove(rc,1);
        if(res.get(rc)==0) {
            xLabel.setText("");
            xLabel.setDisable(true);
        }
        else
            xLabel.setText("x"+res.get(rc));
        enableResourcesClick(coin,shield,servant,rock);
    }

    /**
     * Makes ImageViews of the Resources not clickable
     *
     * @param coin ImageView of the coin
     * @param shield ImageView of the shield
     * @param servant ImageView of the servant
     * @param rock ImageView of the rock
     */
    @FXML
    private void disableResourcesClick(ImageView coin,ImageView shield,ImageView servant,ImageView rock){
        coin.setDisable(true);
        shield.setDisable(true);
        servant.setDisable(true);
        rock.setDisable(true);
    }

    /**
     * Makes ImageViews of the Resources clickable
     *
     * @param coin ImageView of the coin
     * @param shield ImageView of the shield
     * @param servant ImageView of the servant
     * @param rock ImageView of the rock
     */
    @FXML
    private void enableResourcesClick(ImageView coin,ImageView shield,ImageView servant,ImageView rock){
        coin.setDisable(false);
        shield.setDisable(false);
        servant.setDisable(false);
        rock.setDisable(false);
    }

    /**
     * Sets modal for the basic and leader production
     * @param isBasic true if it's a basic production
     * @param card leader card (can be null if basic production)
     * @param modal modal
     */
    public void setModal(boolean isBasic,LeaderCard card,Stage modal){}

    /**
     * Sets modal for a leader deposit move
     * @param toLeader true if to Leader
     * @param leaderDeposit Countertop of the leader
     * @param clientDashboard clientDashboard
     * @param modal modal
     * @param leaderPosition position of the leader card
     */
    public void setModal(boolean toLeader,CounterTop leaderDeposit,ClientDashboardController clientDashboard,Stage modal,int leaderPosition){}

    /**
     * Launching a modal stage for particular actions
     * @param fxml fxml path of the scene to launch
     * @return a stage containing the selected scene
     */
    protected Stage launchModal(String fxml){
        GuiController controller = null;
        Stage modal = new Stage();
        modal.setScene(new Scene(new Pane()));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource(fxml));
        Pane pane;
        try {
            pane = loader.load();
            modal.getScene().setRoot(pane);
            controller = loader.getController();
        } catch (IOException e) {
            System.out.println("IOException while setting layout: "+e.getMessage());
        }
        getGuiManager().setCurrentController(controller);
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("Choose");
        modal.setResizable(false);
        modal.getIcons().add(new Image(this.getClass().getResourceAsStream("/img/javaFX/icon.png")));
        return modal;
    }
}
