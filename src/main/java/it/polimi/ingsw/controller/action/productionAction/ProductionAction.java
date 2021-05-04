package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.ProductionAlreadyDoneException;
import it.polimi.ingsw.model.PlayerDashboard;

import java.util.ArrayList;

public class ProductionAction extends Action {
    private ArrayList<LeaderCardProductionAction> leaderCardProductions;
    private ArrayList<DevCardProductionAction> devCardProductions;
    private BasicProductionAction basicProduction;

    public ProductionAction(BasicProductionAction basicProduction){
        this.leaderCardProductions = new ArrayList<>();
        this.devCardProductions = new ArrayList<>();
        this.basicProduction = basicProduction;
    }
    public ProductionAction(LeaderCardProductionAction leaderCardProduction){
        this.leaderCardProductions = new ArrayList<>();
        this.leaderCardProductions.add(0,leaderCardProduction);
        this.devCardProductions = new ArrayList<>();
        this.basicProduction = null;
    }
    public ProductionAction(DevCardProductionAction devCardProduction){
        this.leaderCardProductions = new ArrayList<>();
        this.devCardProductions = new ArrayList<>();
        this.devCardProductions.add(0,devCardProduction);
        this.basicProduction = null;
    }

    public void addLeaderCardProduction(LeaderCardProductionAction leaderCardProduction,PlayerDashboard player) {
        if(this.leaderCardProductions.contains(leaderCardProduction)||this.leaderCardProductions.size()==2)
            throw new ProductionAlreadyDoneException();
        else {
            this.leaderCardProductions.add(0, leaderCardProduction);
            this.leaderCardProductions.get(0).useAction(player);
        }
    }

    public void addDevCardProduction(DevCardProductionAction devCardProduction, PlayerDashboard player) {
        if(this.devCardProductions.contains(devCardProduction)||this.leaderCardProductions.size()==3)
            throw new ProductionAlreadyDoneException();
        else {
            this.devCardProductions.add(0, devCardProduction);
            this.devCardProductions.get(0).useAction(player);
        }
    }
    public void addBasicProduction(BasicProductionAction basicProduction,PlayerDashboard player) {
        if(this.basicProduction!=null)
            throw new ProductionAlreadyDoneException();
        else {
            this.basicProduction = basicProduction;
            this.basicProduction.useAction(player);
        }
    }
}
