package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.model.ResourceCount;
import it.polimi.ingsw.model.Storage;
import it.polimi.ingsw.model.card.DevelopmentCard;
import it.polimi.ingsw.model.card.LeaderCard;
import it.polimi.ingsw.model.enumeration.Resource;
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
    public void setBuyCard(int row, int col, DevelopmentCard card){}

    protected void setImage(ImageView image, String path){
        if(path == null)
            image.setImage(null);
        else
            image.setImage(new Image(this.getClass().getResourceAsStream(path)));
    }

    protected void setStorage(Storage storage,ImageView firstRowImage,ImageView secondRowImage1,ImageView secondRowImage2,ImageView thirdRowImage1,ImageView thirdRowImage2,ImageView thirdRowImage3){
        ArrayList<CounterTop> shelves = storage.getShelvesArray();
        if(shelves.size()!=0){
            for(int i=0;i<shelves.size();i++)
                setStorageRow(shelves.get(i), i + 1,firstRowImage,secondRowImage1,secondRowImage2,thirdRowImage1,thirdRowImage2,thirdRowImage3);
        }
    }

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

    protected String getCounterTopImage(Resource res){
        switch(res){
            case COIN:  return "/img/punchboard/coin2.png";
            case ROCK:  return "/img/punchboard/stone2.png";
            case SHIELD:  return "/img/punchboard/shield2.png";
            case SERVANT:  return "/img/punchboard/servant2.png";
            default: return null;
        }
    }

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

    public void setModal(boolean isInput){}

    protected void launchChooseResources(boolean isInput){
        GuiController controller = null;
        Stage modal = new Stage();
        modal.setScene(new Scene(new Pane()));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxml/chooseResources.fxml"));
        Pane pane;
        try {
            pane = loader.load();
            modal.getScene().setRoot(pane);
            controller = loader.getController();
        } catch (IOException e) {
            System.out.println("IOException while setting layout: "+e.getMessage());
        }
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initStyle(StageStyle.UNDECORATED);
        modal.setResizable(false);
        modal.getIcons().add(new Image(this.getClass().getResourceAsStream("/img/javaFX/icon.png")));
        controller.setModal(isInput);
        Platform.runLater(modal::show);
    }

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

    @FXML
    private void disableResourcesClick(ImageView coin,ImageView shield,ImageView servant,ImageView rock){
        coin.setDisable(true);
        shield.setDisable(true);
        servant.setDisable(true);
        rock.setDisable(true);
    }

    @FXML
    private void enableResourcesClick(ImageView coin,ImageView shield,ImageView servant,ImageView rock){
        coin.setDisable(false);
        shield.setDisable(false);
        servant.setDisable(false);
        rock.setDisable(false);
    }
}
