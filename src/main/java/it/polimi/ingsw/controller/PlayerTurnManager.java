package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;

public class PlayerTurnManager {
    private PlayerDashboard player;
    private Action action;

    public PlayerTurnManager(PlayerDashboard player) {
        this.player = player;
        this.action = null;
    }

    public PlayerDashboard getPlayer() {
        return player;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void useAction(){
        action.useAction(player);
    }

    public Action getAction() {
        return action;
    }

    public void onUpdate() {

    }

    public void endTurn() {

    }
}
