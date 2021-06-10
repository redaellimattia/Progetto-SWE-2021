package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.view.cli.Cli;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class askResourcesToMoveController extends GuiController{
    @FXML
    private Button confirmButton;
    @FXML
    private AnchorPane one,two;
    private int numSelected;
    private ClientDashboardController clientDashboard;
    private boolean toLeader;
    private CounterTop leaderDeposit;
    private Stage modal;

    /**
     * set a modal asking the number of resources a player wants to move to/from a leader.
     * @param toLeader true if moving TO a leader
     * @param leaderDeposit the interested leader deposit
     * @param clientDashboard the clientDashboardController who called the modal
     * @param modal the modal created and called
     */
    @Override
    public void setModal(boolean toLeader, CounterTop leaderDeposit, ClientDashboardController clientDashboard, Stage modal){
        numSelected = 0;
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
        this.leaderDeposit = leaderDeposit;
        this.modal=modal;
        confirmButton.setDisable(true);
    }

    /**
     * called upon the click of the "confirm" button, either ends the action sending a message
     * or set some parameters on the calling clientDashboardController
     * @param mouseEvent
     */
    public void confirmClick(MouseEvent mouseEvent) {
        if(toLeader)
            clientDashboard.getGuiManager().sendMoveToLeader(leaderDeposit.getResourceType(),numSelected);
        else {
            clientDashboard.setNumberOfResourcesLeaderMove(numSelected);
            clientDashboard.setResourceTypeMove(leaderDeposit.getResourceType());
        }
        modal.close();
    }

    /**
     * player selected the number one
     * @param mouseEvent
     */
    public void selectedOne(MouseEvent mouseEvent) {
        numSelected = 1;
        one.setDisable(true);
        two.setDisable(true);
        two.setVisible(false);
        confirmButton.setDisable(false);
    }

    /**
     * player selected the number two
     * @param mouseEvent
     */
    public void selectedTwo(MouseEvent mouseEvent) {
        numSelected = 2;
        one.setDisable(true);
        two.setDisable(true);
        one.setVisible(false);
        confirmButton.setDisable(false);
    }
}
