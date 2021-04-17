package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;

public class PlayerTurnManager {
    private PlayerDashboard player;
    private Action action;

    public PlayerTurnManager(PlayerDashboard player, Action action) {
        this.player = player;
        this.action = action;
    }



    public void onUpdate() {

    }

    public void endTurn() {

    }
}
