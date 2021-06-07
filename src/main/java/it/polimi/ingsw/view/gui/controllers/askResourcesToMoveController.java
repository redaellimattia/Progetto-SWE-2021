package it.polimi.ingsw.view.gui.controllers;

import it.polimi.ingsw.model.CounterTop;
import it.polimi.ingsw.view.cli.Cli;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class askResourcesToMoveController extends GuiController{
    @FXML
    private AnchorPane one,two;
    private int numSelected;
    private ClientDashboardController clientDashboard;
    private boolean toLeader;
    private CounterTop leaderDeposit;
    private Stage modal;
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
    }
    public void confirmClick(MouseEvent mouseEvent) {
        if(toLeader){
            clientDashboard.getGuiManager().getClientManager().moveLeaderResources(leaderDeposit.getResourceType(),numSelected,false);
        }
        else {
            clientDashboard.setNumberOfResourcesLeaderMove(numSelected);
            clientDashboard.setResourceTypeMove(leaderDeposit.getResourceType());
        }

        modal.close();
    }

    public void selectedOne(MouseEvent mouseEvent) {
        numSelected = 1;
        one.setDisable(true);
        two.setDisable(true);
    }

    public void selectedTwo(MouseEvent mouseEvent) {
        numSelected = 2;
        one.setDisable(true);
        two.setDisable(true);
    }
}
