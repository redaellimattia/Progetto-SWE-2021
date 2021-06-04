package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class PaymentController extends GuiController{
    @FXML //XCHOSEN
    private Label xCoinChosen,xShieldChosen,xServantChosen,xRockChosen;
    @FXML //CHESTCHOSEN
    private ImageView chestCoinChosen,chestShieldChosen,chestServantChosen,chestRockChosen;
    @FXML //STORAGECHOSEN
    private ImageView firstRowImageChosen,secondRowImage1Chosen,secondRowImage2Chosen,thirdRowImage1Chosen,thirdRowImage2Chosen,thirdRowImage3Chosen;
    @FXML //XYOUHAVE
    private Label xCoinYouHave,xShieldYouHave,xServantYouHave,xRockYouHave;
    @FXML //TO PAY
    private ImageView coinStillToPay,shieldStillToPay,servantStillToPay,rockStillToPay;
    @FXML //XSTILL TO PAY
    private Label xCoinStillToPay,xShieldStillToPay,xServantStillToPay,xRockStillToPay;
    @FXML //CHESTYOUHAVE
    private ImageView chestCoinYouHave,chestShieldYouHave,chestServantYouHave,chestRockYouHave;
    @FXML //STORAGEYOUHAVE
    private ImageView firstRowImageYouHave,secondRowImage1YouHave,secondRowImage2YouHave,thirdRowImage1YouHave,thirdRowImage2YouHave,thirdRowImage3YouHave;

    private PlayerDashboard playerDashboard;


    @FXML
    @Override
    public void initialize() {
        super.setGuiManager(GuiManager.getInstance());
        playerDashboard = getGuiManager().getClientManager().getThisClientDashboard();
        //Setting up what the player's have
        setStorage(playerDashboard.getStorage(),firstRowImageYouHave,secondRowImage1YouHave,secondRowImage2YouHave,thirdRowImage1YouHave,thirdRowImage2YouHave,thirdRowImage3YouHave);
        setChest(playerDashboard.getChest(),xCoinYouHave,xShieldYouHave,xRockYouHave,xServantYouHave);
    }
}
