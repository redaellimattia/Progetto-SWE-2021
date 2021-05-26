package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.ProductionAlreadyDoneException;
import it.polimi.ingsw.model.PlayerDashboard;

import java.util.ArrayList;

public class ProductionAction extends Action {
    private ArrayList<LeaderCardProductionAction> leaderCardProductions;
    private ArrayList<DevCardProductionAction> devCardProductions;
    private BasicProductionAction basicProduction;

    /**
     * Creating a ProductionAction as main action
     *
     */
    public ProductionAction(){
        this.leaderCardProductions = new ArrayList<>();
        this.devCardProductions = new ArrayList<>();
        this.basicProduction = null;
    }


    /**
     * Adding a leaderCardProduction
     *
     * @param leaderCardProduction leaderCardProduction chosen by the client to do in this turn
     */
    @Override
    public void addLeaderCardProduction(LeaderCardProductionAction leaderCardProduction,PlayerDashboard player) {
        if(this.leaderCardProductions.contains(leaderCardProduction)||this.leaderCardProductions.size()==2)
            throw new ProductionAlreadyDoneException(player);
        else {
            this.leaderCardProductions.add(0, leaderCardProduction);
            leaderCardProduction.useAction(player);
        }
    }

    /**
     * Adding a devCardProduction
     *
     * @param devCardProduction devCardProduction chosen by the client to do in this turn
     */
    @Override
    public void addDevCardProduction(DevCardProductionAction devCardProduction,PlayerDashboard player) {
        if(this.devCardProductions.contains(devCardProduction)||this.leaderCardProductions.size()==3)
            throw new ProductionAlreadyDoneException(player);
        else {
            this.devCardProductions.add(0, devCardProduction);
            devCardProduction.useAction(player); //Setting LastAddedAction
        }
    }

    /**
     * Adding a basicProduction
     *
     * @param basicProduction basicProduction chosen by the client to do in this turn
     */
    @Override
    public void addBasicProduction(BasicProductionAction basicProduction,PlayerDashboard player) {
        if(this.basicProduction!=null) //Throw exception if basicProduction is not null, basicProduction already done
            throw new ProductionAlreadyDoneException(player);
        else {
            this.basicProduction = basicProduction; //Updating basicProduction if possible
            basicProduction.useAction(player); //Setting LastAddedAction
        }
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
