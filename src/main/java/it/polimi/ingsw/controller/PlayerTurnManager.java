package it.polimi.ingsw.controller;

import it.polimi.ingsw.controller.action.Action;
import it.polimi.ingsw.model.PlayerDashboard;

public class PlayerTurnManager {
    private PlayerDashboard player;
    private Action action;
    private Parameter parameter;

    public PlayerTurnManager(PlayerDashboard player, Action action, Parameter parameter) {
        this.player = player;
        this.action = action;
        this.parameter = parameter;
    }



    public void onUpdate() {

    }

    public void endTurn() {

    }
}
