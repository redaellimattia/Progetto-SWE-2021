package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Ask resources to move scene controller
 */
public class askResourcesToMoveController extends GuiController{
    @FXML
    private Button one,two;
    private ClientDashboardController clientDashboard;
    private boolean toLeader;
    private Stage modal;
    private int leaderPosition;

    @Override
    @FXML
    public void initialize(){
        super.setGuiManager(GuiManager.getInstance());
    }

    /**
     * Set a modal asking the number of resources a player wants to move to/from a leader.
     * @param toLeader true if moving TO a leader
     * @param leaderDeposit the interested leader deposit
     * @param clientDashboard the clientDashboardController who called the modal
     * @param modal the modal created and called
     */
    @Override
    public void setModal(boolean toLeader, CounterTop leaderDeposit, ClientDashboardController clientDashboard, Stage modal,int leaderPosition){
        if(!toLeader){
           if(leaderDeposit.getContent()==1)
               two.setDisable(true);
           if(leaderDeposit.getContent()==0) {
               one.setDisable(true);
               two.setDisable(true);
           }
        }
        else{
            if(leaderDeposit.getContent()==1)
                two.setDisable(true);
        }
        this.clientDashboard = clientDashboard;
        this.toLeader=toLeader;
        this.modal=modal;
        this.leaderPosition = leaderPosition;
    }

    /**
     * Called after clicking 1 or 2 in the askResourcesToMove Modal
     * either send a message or set the clientDashboardController params for the leaderMove
     * @param numSelected number of selected resources
     */
    public void confirmClick(int numSelected) {
        if(toLeader)
            getGuiManager().sendMoveToLeader(clientDashboard.getFirstCounterTopSwapped(),leaderPosition,numSelected);
        else
            clientDashboard.setNumberOfResourcesLeaderMove(numSelected);
        modal.close();
    }

    /**
     * Player selected the number one
     */
    public void selectedOne() {
        one.setDisable(true);
        two.setDisable(true);
        two.setVisible(false);
        confirmClick(1);
    }

    /**
     * Player selected the number two
     */
    public void selectedTwo() {
        one.setDisable(true);
        two.setDisable(true);
        one.setVisible(false);
        confirmClick(2);
    }
}
