package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.IllegalActionException;
import it.polimi.ingsw.model.PlayerDashboard;

public class PlayerTurnManager {
    private PlayerDashboard player;
    private Action action;
    private Action sideAction;

    /**
     *
     * @param player who's now playing
     */
    public PlayerTurnManager(PlayerDashboard player) {
        this.player = player;
        this.action = null;
    }

    /**
     *
     * @return player actually playing
     */
    public PlayerDashboard getPlayer() {
        return player;
    }

    /**
     *
     * @param action main action that the player want to execute
     */
    public void setAction(Action action) {
        if(this.action==null) //First action
            this.action = action;
        else
            throw new IllegalActionException();
    }

    /**
     *
     * @param action
     */
    public void setSideAction(Action action) {
        this.sideAction = action;
    }

    /**
     * use the main action upon receiving a message
     */
    public void useAction(){
        action.useAction(player);
    }

    /**
     * use a side action upon receiving a message
     */
    public void useSideAction(){
        sideAction.useAction(player);
    }

    /**
     *  used to call the endAction method
     * @param player player who sent the EndActionMessage
     */
    public void endAction(PlayerDashboard player ){action.endAction(player);}

    public Action getAction() {
        return action;
    }

    public Action getSideAction() {
        return sideAction;
    }

    public void onUpdate() {

    }

    public void endTurn() {

    }
}
