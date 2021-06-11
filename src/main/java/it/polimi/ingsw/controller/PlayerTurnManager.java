package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.controller.action.marketAction.AtomicMarketAction;
import it.polimi.ingsw.controller.action.productionAction.BasicProductionAction;
import it.polimi.ingsw.controller.action.productionAction.DevCardProductionAction;
import it.polimi.ingsw.controller.action.productionAction.LeaderCardProductionAction;
import it.polimi.ingsw.controller.action.productionAction.ProductionAction;
import it.polimi.ingsw.exceptions.MasterOfRenaissanceRuntimeException;
import it.polimi.ingsw.exceptions.action.IllegalActionException;
import it.polimi.ingsw.model.PlayerDashboard;
import it.polimi.ingsw.network.server.Server;

import java.util.ArrayList;
import java.util.logging.Level;

public class PlayerTurnManager {
    private PlayerDashboard player;
    private Action action;
    private Action sideAction;
    private ArrayList<AtomicMarketAction> marketChoices;

    /**
     *
     * @param player who's now playing
     */
    public PlayerTurnManager(PlayerDashboard player) {
        this.player = player;
        action = null;
        marketChoices = new ArrayList<>();
    }

    /**
     *
     * @return player actually playing
     */
    public PlayerDashboard getPlayer() {
        return player;
    }

    public void setPlayer(PlayerDashboard player) {
        this.player = player;
    }

    /**
     *
     * @param action main action that the player want to execute
     */
    public void setAction(Action action) {
        if(this.action==null) //First action
            this.action = action;
        else
            throw new IllegalActionException(player);
    }

    /**
     *
     * @param action sideAction chosen by the client
     */
    public void setSideAction(Action action) {
        this.sideAction = action;
    }

    /**
     * use the main action upon receiving a message
     */
    public boolean useAction(){
        try {
            action.useAction(player,this);
            return true;
        }catch(MasterOfRenaissanceRuntimeException e){Server.LOGGER.log(Level.SEVERE,e.getMessage());}
        return false;
    }

    /**
     * use a side action upon receiving a message
     */
    public boolean useSideAction(){
        try {
            return sideAction.useAction(player,this);
        }catch(MasterOfRenaissanceRuntimeException e){
            Server.LOGGER.log(Level.SEVERE,e.getMessage());}
        return false;
    }
    /**
     *  used to call the endAction method
     * @param player player who sent the EndActionMessage
     */
    public void endAction(PlayerDashboard player ){action.endAction(player);}

    public Action getAction() {
        return action;
    }

    public ArrayList<AtomicMarketAction> getMarketChoices() {
        return marketChoices;
    }


    /**
     * Adds an atomic production to the main action or creates a new MainAction as ProductionAction
     *
     * @param basicProduction basicProduction chosen by the client
     */
    public boolean addBasicProduction(BasicProductionAction basicProduction){
        if(action==null) {
            action = new ProductionAction();
        }
        return action.addBasicProduction(basicProduction,player,this);
    }

    /**
     * Adds an atomic production to the main action or creates a new MainAction as ProductionAction
     *
     * @param devCardProduction devCardProduction chosen by the client
     */
    public boolean addDevCardProduction(DevCardProductionAction devCardProduction){
        if(action==null) {
            action = new ProductionAction();
        }
        return action.addDevCardProduction(devCardProduction,player,this);
    }

    /**
     * Adds an atomic production to the main action or creates a new MainAction as ProductionAction
     *
     * @param leaderCardProduction leaderCardProduction chosen by the client
     */
    public boolean addLeaderCardProduction(LeaderCardProductionAction leaderCardProduction){
        if(action==null) {
            action = new ProductionAction();
        }
        return action.addLeaderCardProduction(leaderCardProduction,player,this);
    }

    /**
     * Resets the action
     */
    public void resetMainAction(){
        this.action = null;
        if(this.marketChoices.size()>0)
            this.marketChoices = new ArrayList<>();

    }

    /**
     * Adds an atomicMarketAction to the marketChoices buffer
     * @param atomicMarketAction the atomicMarketAction chosen by the client
     */
    public void addMarketChoice(AtomicMarketAction atomicMarketAction) {
        marketChoices.add(atomicMarketAction);
    }
}
