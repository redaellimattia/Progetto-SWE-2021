package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.PlayerDashboard;

public class PlayerTurnManager {

    public PlayerTurnManager(PlayerDashboard player, Action action) {
        this.player = player;
        this.action = action;
    }

    private PlayerDashboard player;
    private Action action;

    public void onUpdate() {

    }

    public void endTurn() {

    }
}
