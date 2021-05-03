package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.exceptions.action.IllegalActionException;
import it.polimi.ingsw.model.PlayerDashboard;

public class PlayerTurnManager {
    private PlayerDashboard player;
    private Action action;
    private Action sideAction;

    public PlayerTurnManager(PlayerDashboard player) {
        this.player = player;
        this.action = null;
    }

    public PlayerDashboard getPlayer() {
        return player;
    }

    public void setAction(Action action) {
        if(this.action==null) //First action
            this.action = action;
        else
            throw new IllegalActionException();
    }

    public void setSideAction(Action action) {
        this.sideAction = action;
    }

    public void useAction(){
        action.useAction(player);
    }

    public void useSideAction(){
        sideAction.useAction(player);
    }

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
