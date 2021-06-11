package it.polimi.ingsw.controller.action.productionAction;

import it.polimi.ingsw.controller.PlayerTurnManager;
import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.exceptions.action.ProductionAlreadyDoneException;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.network.server.Server;

import java.util.ArrayList;
import java.util.logging.Level;

public class ProductionAction extends Action {
    private final ArrayList<LeaderCardProductionAction> leaderCardProductions;
    private final ArrayList<DevCardProductionAction> devCardProductions;
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
    public boolean addLeaderCardProduction(LeaderCardProductionAction leaderCardProduction, PlayerDashboard player,PlayerTurnManager turnManager) {
        if(this.leaderCardProductions.contains(leaderCardProduction)||this.leaderCardProductions.size()==2)
            throw new ProductionAlreadyDoneException(player,turnManager);
        else {
            this.leaderCardProductions.add(0, leaderCardProduction);
            try {
                return leaderCardProduction.useAction(player,turnManager);
            }catch(MasterOfRenaissanceRuntimeException e){Server.LOGGER.log(Level.SEVERE,e.getMessage());}
        }
        return false;
    }

    /**
     * Adding a devCardProduction
     *
     * @param devCardProduction devCardProduction chosen by the client to do in this turn
     */
    @Override
    public boolean addDevCardProduction(DevCardProductionAction devCardProduction,PlayerDashboard player,PlayerTurnManager turnManager) {
        if(this.devCardProductions.contains(devCardProduction)||this.leaderCardProductions.size()==3)
            throw new ProductionAlreadyDoneException(player,turnManager);
        else {
            this.devCardProductions.add(0, devCardProduction);
            try {
                return devCardProduction.useAction(player,turnManager); //Setting LastAddedAction
            }catch(MasterOfRenaissanceRuntimeException e){Server.LOGGER.log(Level.SEVERE,e.getMessage());}
        }
        return false;
    }

    /**
     * Adding a basicProduction
     *
     * @param basicProduction basicProduction chosen by the client to do in this turn
     */
    @Override
    public boolean addBasicProduction(BasicProductionAction basicProduction,PlayerDashboard player,PlayerTurnManager turnManager) {
        if(this.basicProduction!=null) //Throw exception if basicProduction is not null, basicProduction already done
            throw new ProductionAlreadyDoneException(player,turnManager);
        else {
            this.basicProduction = basicProduction; //Updating basicProduction if possible
            try {
                return basicProduction.useAction(player,turnManager); //Setting LastAddedAction
            }catch(MasterOfRenaissanceRuntimeException e){
                Server.LOGGER.log(Level.SEVERE,e.getMessage());}
        }
        return false;
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
