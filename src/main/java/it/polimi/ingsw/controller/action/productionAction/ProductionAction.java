package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.ProductionAlreadyDoneException;
import it.polimi.ingsw.model.PlayerDashboard;

import java.util.ArrayList;

public class ProductionAction extends Action {
    private ArrayList<LeaderCardProductionAction> leaderCardProductions;
    private ArrayList<DevCardProductionAction> devCardProductions;
    private BasicProductionAction basicProduction;
    private Action lastAddedAction;

    /**
     * Starting ProductionAction with a basicProduction
     *
     * @param basicProduction basicProduction is chosen by the client as first production to do in this turn
     */
    public ProductionAction(BasicProductionAction basicProduction){
        this.leaderCardProductions = new ArrayList<>();
        this.devCardProductions = new ArrayList<>();
        this.basicProduction = basicProduction;
        this.lastAddedAction = basicProduction; //Setting LastAddedAction
    }

    /**
     * Starting ProductionAction with a leaderCardProduction
     *
     * @param leaderCardProduction leaderCardProduction is chosen by the client as first production to do in this turn
     */
    public ProductionAction(LeaderCardProductionAction leaderCardProduction){
        this.leaderCardProductions = new ArrayList<>();
        this.leaderCardProductions.add(0,leaderCardProduction);
        this.lastAddedAction = leaderCardProduction; //Setting LastAddedAction
        this.devCardProductions = new ArrayList<>();
        this.basicProduction = null;
    }

    /**
     * Starting ProductionAction with a devCardProduction
     *
     * @param devCardProduction devCardProduction is chosen by the client as first production to do in this turn
     */
    public ProductionAction(DevCardProductionAction devCardProduction){
        this.leaderCardProductions = new ArrayList<>();
        this.devCardProductions = new ArrayList<>();
        this.devCardProductions.add(0,devCardProduction);
        this.lastAddedAction = devCardProduction; //Setting LastAddedAction
        this.basicProduction = null;
    }

    /**
     * Adding a leaderCardProduction
     *
     * @param leaderCardProduction leaderCardProduction chosen by the client to do in this turn
     */
    public void addLeaderCardProduction(LeaderCardProductionAction leaderCardProduction) {
        if(this.leaderCardProductions.contains(leaderCardProduction)||this.leaderCardProductions.size()==2)
            throw new ProductionAlreadyDoneException();
        else {
            this.leaderCardProductions.add(0, leaderCardProduction);
            this.lastAddedAction = leaderCardProduction; //Setting LastAddedAction
        }
    }

    /**
     * Adding a devCardProduction
     *
     * @param devCardProduction devCardProduction chosen by the client to do in this turn
     */
    public void addDevCardProduction(DevCardProductionAction devCardProduction) {
        if(this.devCardProductions.contains(devCardProduction)||this.leaderCardProductions.size()==3)
            throw new ProductionAlreadyDoneException();
        else {
            this.devCardProductions.add(0, devCardProduction);
            this.lastAddedAction = devCardProduction; //Setting LastAddedAction
        }
    }

    /**
     * Adding a basicProduction
     *
     * @param basicProduction basicProduction chosen by the client to do in this turn
     */
    public void addBasicProduction(BasicProductionAction basicProduction) {
        if(this.basicProduction!=null) //Throw exception if basicProduction is not null, basicProduction already done
            throw new ProductionAlreadyDoneException();
        else {
            this.basicProduction = basicProduction; //Updating basicProduction if possible
            this.lastAddedAction = basicProduction; //Setting LastAddedAction
        }
    }

    @Override
    public void useAction(PlayerDashboard player) {
        this.lastAddedAction.useAction(player);
    }

    /**
     * EMPTY BUFFER
     *
     * @param player player that is ending the action
     */
    @Override
    public void endAction(PlayerDashboard player) {
        player.emptyBufferProduction();
    }
}
